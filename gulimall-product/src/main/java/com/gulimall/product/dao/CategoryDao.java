package com.gulimall.product.dao;

import com.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-23 11:30:09
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
