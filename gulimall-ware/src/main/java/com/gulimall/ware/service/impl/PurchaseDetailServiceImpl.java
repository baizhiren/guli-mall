package com.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.gulimall.ware.dao.PurchaseDetailDao;
import com.gulimall.ware.entity.PurchaseDetailEntity;
import com.gulimall.ware.service.PurchaseDetailService;
import org.springframework.util.StringUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<PurchaseDetailEntity>();

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            //purchase_id  sku_id
        queryWrapper.and(w->{
            w.eq("purchase_id",key).or().eq("sku_id",key);
        });
    }

    String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)){
        //purchase_id  sku_id
        queryWrapper.eq("status",status);
    }

    String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)){
        //purchase_id  sku_id
        queryWrapper.eq("ware_id",wareId);
    }

    IPage<PurchaseDetailEntity> page = this.page(
            new Query<PurchaseDetailEntity>().getPage(params),
            queryWrapper
    );

        return new PageUtils(page);
    }

    @Override
    public List<PurchaseDetailEntity> getListByPurchaseId(Long id) {
        return this.list(new LambdaQueryWrapper<PurchaseDetailEntity>().
                eq(PurchaseDetailEntity::getPurchaseId, id));
    }

}