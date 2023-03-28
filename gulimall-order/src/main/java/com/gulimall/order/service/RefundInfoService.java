package com.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.gulimall.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-27 19:53:15
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

