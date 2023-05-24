package com.gulimall.coupon.dao;

import com.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-27 21:17:45
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
