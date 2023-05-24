package com.gulimall.product.vo;

import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupResp extends AttrGroupEntity {
    List<AttrEntity> attrs;
}
