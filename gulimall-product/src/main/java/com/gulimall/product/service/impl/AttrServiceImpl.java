package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gulimall.product.constant.Attr;
import com.gulimall.product.entity.*;
import com.gulimall.product.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.gulimall.product.dao.AttrDao;
import org.springframework.transaction.annotation.Transactional;
import com.gulimall.product.vo.AttrResp;
import com.gulimall.product.vo.AttrVo;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;


    @Autowired
    AttrGroupService attrGroupServicea;


    @Autowired
    CategoryService categoryService;


    @Autowired
    ProductAttrValueService productAttrValueService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        this.save(attrEntity);

        if (attrEntity.getAttrType() == Attr.AttrType.Base.getCode() && attrVo.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
        }
    }

    @Override
    public PageUtils queryAttrPage(Map<String, Object> params, Long catelogId, String attr_type) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        int code = -1;
        if (attr_type.equalsIgnoreCase(Attr.AttrType.Base.getType())) code = Attr.AttrType.Base.getCode();
        else if (attr_type.equalsIgnoreCase(Attr.AttrType.Sale.getType())) code = Attr.AttrType.Sale.getCode();

        wrapper.eq("attr_type", code);

        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(attrEntityQueryWrapper -> {
                attrEntityQueryWrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        PageUtils pageUtils = new PageUtils(page);

        List<AttrEntity> records = page.getRecords();
        List<AttrResp> list = records.stream().map(attrEntity -> {
            //查询groupName
            AttrResp attrResp = new AttrResp();
            BeanUtils.copyProperties(attrEntity, attrResp);

            if (attr_type.equalsIgnoreCase(Attr.AttrType.Base.getType())) {
                AttrAttrgroupRelationEntity realtion = attrAttrgroupRelationService.getOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId())
                );
                if (realtion != null) {
                    Long attrGroupId = realtion.getAttrGroupId();

                    AttrGroupEntity attr_group = attrGroupServicea.getOne(
                            new QueryWrapper<AttrGroupEntity>().eq("attr_group_id", attrGroupId)
                    );
                    if (attr_group != null) {
                        attrResp.setGroupName(attr_group.getAttrGroupName());
                        //attrResp.setAttrGroupId(attrGroupId);
                    }

                }
            }
            CategoryEntity category = categoryService.getOne(
                    new QueryWrapper<CategoryEntity>().eq("cat_id", attrEntity.getCatelogId())
            );
            if (category != null)
                attrResp.setCatelogName(category.getName());

            return attrResp;
        }).collect(Collectors.toList());

        pageUtils.setList(list);

        return pageUtils;
    }


    @Override
    public AttrResp getAttrInfo(Long attrId) {
        AttrEntity attrEntity = getOne(new QueryWrapper<AttrEntity>().eq("attr_id", attrId));
        AttrResp attrResp = new AttrResp();
        BeanUtils.copyProperties(attrEntity, attrResp);

        if (attrEntity.getAttrType() == Attr.AttrType.Base.getCode()) {

            AttrAttrgroupRelationEntity realtion = attrAttrgroupRelationService.getOne(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId())
            );
            if (realtion != null) {
                Long attrGroupId = realtion.getAttrGroupId();
                AttrGroupEntity attr_group = attrGroupServicea.getOne(
                        new QueryWrapper<AttrGroupEntity>().eq("attr_group_id", attrGroupId)
                );
                if (attr_group != null) {
                    attrResp.setGroupName(attr_group.getAttrGroupName());
                    attrResp.setAttrGroupId(attrGroupId);
                }
            }
        }

        CategoryEntity category = categoryService.getOne(
                new QueryWrapper<CategoryEntity>().eq("cat_id", attrEntity.getCatelogId())
        );
        if (category != null) {
            attrResp.setCatelogName(category.getName());
            Long[] pathByCategoryId = categoryService.getPathByCategoryId(category.getCatId());
            attrResp.setCatelogPath(pathByCategoryId);
        }

        return attrResp;
    }

    @Override
    public void updateAttr(AttrResp attrResp) {
        //修改关联表
        Long attrGroupId = attrResp.getAttrGroupId();
        Long attrId = attrResp.getAttrId();

        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrResp, attrEntity);


        this.update(attrEntity, new UpdateWrapper<AttrEntity>().eq("attr_id", attrId));

        if (attrEntity.getAttrType() == Attr.AttrType.Base.getCode() && attrGroupId != null) {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrId(attrId);
            attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);

            boolean count = attrAttrgroupRelationService.update(attrAttrgroupRelationEntity,
                    new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));

            //如果之前没有记录，就新增
            if (!count) {
                attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
            }
        }
    }

    @Override
    public List<ProductAttrValueEntity> baseListBySpu(Long spuId) {
        return productAttrValueService.list(new LambdaQueryWrapper<ProductAttrValueEntity>().
                eq(ProductAttrValueEntity::getSpuId, spuId));
    }

    @Override
    public void updateAttrBySpu(Long spuId, List<ProductAttrValueEntity> attrValueEntities) {
        productAttrValueService.remove(new LambdaQueryWrapper<ProductAttrValueEntity>().
                eq(ProductAttrValueEntity::getSpuId, spuId));
        List<ProductAttrValueEntity> collect = attrValueEntities.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect);
    }

}