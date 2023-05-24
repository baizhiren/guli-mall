package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.vo.AttrGroupResp;
import com.gulimall.product.vo.GroupAttrRelation;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-23 11:30:09
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long categoryId);

    List<AttrEntity> getAttrsByGroup(Long attrGroupId);

    void deleteRelation(GroupAttrRelation[] relation);

    PageUtils getNoAttrRelation(Map<String, Object> params, Long attrGroupId);

    void saveRelation(List<AttrAttrgroupRelationEntity> relationEntityList);

    List<AttrGroupResp> getAttrsByCategory(Long cateLogId);
}

