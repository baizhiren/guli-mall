package com.gulimall.product.dao;

import com.gulimall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.gulimall.product.vo.GroupAttrRelation;

/**
 * 属性分组
 * 
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-23 11:30:09
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    void deleteRealtion(@Param("relation") GroupAttrRelation[] relation);
}
