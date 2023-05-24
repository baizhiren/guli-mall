package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.ProductAttrValueEntity;
import com.gulimall.product.vo.AttrResp;
import com.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-23 11:30:09
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void save(AttrVo attrVo);

    PageUtils queryAttrPage(Map<String, Object> params, Long catelogId, String attr_type);

    AttrResp getAttrInfo(Long attrId);

    void updateAttr(AttrResp attrResp);

    List<ProductAttrValueEntity> baseListBySpu(Long spuId);

    void updateAttrBySpu(Long spuId, List<ProductAttrValueEntity> attrValueEntities);
}

