package com.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.gulimall.ware.entity.PurchaseDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-27 19:45:56
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

