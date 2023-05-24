package com.gulimall.product.vo;

import com.gulimall.product.entity.AttrEntity;
import lombok.Data;

@Data
public class AttrResp extends AttrEntity {
    String catelogName;
    String groupName;
    Long[] catelogPath;
    Long attrGroupId;
}
