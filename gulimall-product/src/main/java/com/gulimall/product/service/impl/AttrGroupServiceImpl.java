package com.gulimall.product.service.impl;

import com.gulimall.product.constant.Attr;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.service.AttrAttrgroupRelationService;
import com.gulimall.product.service.AttrService;
import com.gulimall.product.vo.AttrGroupResp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.gulimall.product.dao.AttrGroupDao;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrGroupService;
import com.gulimall.product.vo.GroupAttrRelation;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Resource
    AttrService attrService;

    @Resource
    AttrGroupDao attrGroupDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        String key = (String)params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and((obj) -> {
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }
        if(categoryId != 0){
            wrapper.eq("catelog_id", categoryId);
        }
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<AttrEntity> getAttrsByGroup(Long attrGroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));

        List<Long> AttrIds = relationEntities.stream().map(attrAttrgroupRelationEntity -> {
            return attrAttrgroupRelationEntity.getAttrId();
        }).collect(Collectors.toList());

        List<AttrEntity> attrEntities = new ArrayList<>();
        if(AttrIds.size() != 0)
            attrEntities = attrService.listByIds(AttrIds);

        return attrEntities;

    }

    @Override
    public void deleteRelation(GroupAttrRelation[] relation) {
        List<GroupAttrRelation> groupAttrRelations = Arrays.asList(relation);


        List<AttrAttrgroupRelationEntity> list = groupAttrRelations.stream().map((item) ->{
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());

        attrGroupDao.deleteRealtion(relation);
    }

    @Override
    public PageUtils getNoAttrRelation(Map<String, Object> params, Long attrGroupId) {
        AttrGroupEntity attrGroupEntity = this.getById(attrGroupId);
        Long category = attrGroupEntity.getCatelogId();

        List<AttrGroupEntity> attrGroupEntities  = this.list(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", category));

        List<Long> attrGroupIds = attrGroupEntities.stream().map(item -> item.getAttrGroupId()).collect(Collectors.toList());


        List<AttrAttrgroupRelationEntity> attrRelations = attrAttrgroupRelationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));

        List<Long> attrIds = attrRelations.stream().map(item -> item.getAttrId()).collect(Collectors.toList());

        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        if(attrIds.size() != 0){
            wrapper.notIn("attr_id", attrIds);
        }
        wrapper.eq("catelog_id", category);
        wrapper.eq("attr_type", Attr.AttrType.Base.getCode());

        IPage<AttrEntity> page = attrService.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void saveRelation(List<AttrAttrgroupRelationEntity> relationEntityList) {
        attrAttrgroupRelationService.saveBatch(relationEntityList);
    }

    @Override
    public List<AttrGroupResp> getAttrsByCategory(Long cateLogId) {
        List<AttrGroupEntity> groups = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cateLogId));
        List<AttrGroupResp> collect = groups.stream().map((group) -> {
            AttrGroupResp attrGroupResp = new AttrGroupResp();
            BeanUtils.copyProperties(group, attrGroupResp);
            List<AttrEntity> attrsByGroup = this.getAttrsByGroup(group.getAttrGroupId());
            attrGroupResp.setAttrs(attrsByGroup);
            return attrGroupResp;
        }).collect(Collectors.toList());
        return collect;
    }
}