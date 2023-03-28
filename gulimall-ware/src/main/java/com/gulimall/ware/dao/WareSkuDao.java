package com.gulimall.ware.dao;

import com.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-27 19:45:57
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
