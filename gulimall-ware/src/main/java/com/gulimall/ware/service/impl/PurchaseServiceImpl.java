package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.utils.R;
import com.gulimall.ware.constant.PurchaseDetailStatus;
import com.gulimall.ware.constant.PurchaseEntityStatus;
import com.gulimall.ware.entity.PurchaseDetailEntity;
import com.gulimall.ware.entity.WareSkuEntity;
import com.gulimall.ware.feign.ProductFeignService;
import com.gulimall.ware.service.PurchaseDetailService;
import com.gulimall.ware.service.WareInfoService;
import com.gulimall.ware.service.WareSkuService;
import com.gulimall.ware.vo.MergeVo;
import com.gulimall.ware.vo.PurchaseDoneVo;
import com.gulimall.ware.vo.PurchaseItemDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.gulimall.ware.dao.PurchaseDao;
import com.gulimall.ware.entity.PurchaseEntity;
import com.gulimall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    PurchaseDetailService purchaseDetailService;

    @Autowired
    WareSkuService wareSkuService;

    @Autowired
    ProductFeignService productFeignService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils unreceiveList(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void merge(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if(purchaseId == null){
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setStatus(PurchaseEntityStatus.CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }

        List<Long> items = mergeVo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> collect = items.stream().map(i -> {
            PurchaseDetailEntity detail = new PurchaseDetailEntity();
            detail.setId(i);
            detail.setPurchaseId(finalPurchaseId);
            detail.setStatus(PurchaseDetailStatus.ASSIGNED.getCode());
            return detail;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);

    }

    @Override
    public void receive(List<Long> ids) {
        List<PurchaseEntity> purchaseEntities = this.listByIds(ids);
        List<PurchaseEntity> collect = purchaseEntities.stream().filter((item) -> {
            if (item.getStatus() == PurchaseEntityStatus.CREATED.getCode()
                    || item.getStatus() == PurchaseEntityStatus.ASSIGNED.getCode())
                return true;
            else return false;
        }).map((item) -> {
            item.setStatus(PurchaseEntityStatus.RECEIVE.getCode());
            return item;
        }).collect(Collectors.toList());

        this.updateBatchById(collect);

        collect.forEach((item) -> {
            List<PurchaseDetailEntity> list = purchaseDetailService.getListByPurchaseId(item.getId());
            for(PurchaseDetailEntity detailEntity: list){
                detailEntity.setStatus(PurchaseDetailStatus.BUYING.getCode());
            }
            purchaseDetailService.updateBatchById(list);
        });
    }

    @Override
    @Transactional
    public void done(PurchaseDoneVo purchaseDoneVo) {
        List<PurchaseItemDoneVo> items = purchaseDoneVo.getItems();

        List<PurchaseDetailEntity> details = new ArrayList<>();
        List<Long> itemIds = new ArrayList<>();
        boolean flag = true;
        for (PurchaseItemDoneVo item : items) {
            Long itemId = item.getItemId();
            itemIds.add(itemId);
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            detailEntity.setId(itemId);
            detailEntity.setStatus(item.getStatus());
            details.add(detailEntity);
            if(item.getStatus() == PurchaseDetailStatus.HASERROR.getCode()){
                flag = false;
            }
        }
        purchaseDetailService.updateBatchById(details);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        Long id = purchaseDoneVo.getId();
        purchaseEntity.setId(id);
        purchaseEntity.setStatus(flag ? PurchaseEntityStatus.FINISH.getCode()
                : PurchaseEntityStatus.HASERROR.getCode());

        this.updateById(purchaseEntity);

        //更新库存
        List<PurchaseDetailEntity> purchaseDetailEntities = purchaseDetailService.listByIds(itemIds);
        for (PurchaseDetailEntity purchaseDetailEntity : purchaseDetailEntities) {
            Long skuId = purchaseDetailEntity.getSkuId();
            Long wareId = purchaseDetailEntity.getWareId();
            Integer skuNum = purchaseDetailEntity.getSkuNum();
            WareSkuEntity one = wareSkuService.getOne(new LambdaQueryWrapper<WareSkuEntity>().
                    eq(WareSkuEntity::getSkuId, skuId)
                    .eq(WareSkuEntity::getWareId, wareId)
            );
            if(one == null){
                //插入一条记录
                WareSkuEntity wareSkuEntity = new WareSkuEntity();
                wareSkuEntity.setSkuId(skuId);
                wareSkuEntity.setWareId(wareId);
                wareSkuEntity.setStock(skuNum);
                //远程调用
                try{
                    R info = productFeignService.info(skuId);
                    if(info.getCode() == 0){
                        Map<String, Object> data = (Map<String, Object> )info.get("skuInfo");
                        wareSkuEntity.setSkuName((String)(data.get("skuName")));
                    }
                }catch (Exception e){log.error(e.getMessage());}
                wareSkuService.save(wareSkuEntity);
            }else{
                //更新一条记录
                one.setStock(skuNum + one.getStock());
                wareSkuService.updateById(one);
            }
        }


    }
}