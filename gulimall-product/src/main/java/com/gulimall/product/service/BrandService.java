package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.gulimall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-23 11:30:09
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

