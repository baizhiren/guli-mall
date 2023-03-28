package com.gulimall.order.dao;

import com.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-27 19:53:15
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
