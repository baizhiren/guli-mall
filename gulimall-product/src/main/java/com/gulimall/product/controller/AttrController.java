package com.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.gulimall.product.entity.ProductAttrValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.product.service.AttrService;
import com.common.utils.PageUtils;
import com.common.utils.R;
import com.gulimall.product.vo.AttrResp;
import com.gulimall.product.vo.AttrVo;


/**
 * 商品属性
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-23 11:30:09
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;


    @GetMapping("/{attr_type}/list/{catelogId}")
    public R baseList(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId,
                      @PathVariable("attr_type") String attr_type
                      ){
        PageUtils page = attrService.queryAttrPage(params, catelogId, attr_type);
        return R.ok().put("page", page);
    }

    //product/attr/base/listforspu/{spuId}
    @GetMapping("/base/listforspu/{spuId}")
    public R baseListBySpu(@PathVariable("spuId") Long spuId
    ){
        List<ProductAttrValueEntity> data = attrService.baseListBySpu(spuId);
        return R.ok().put("data", data);
    }

    ///product/attr/update/{spuId}
    @PostMapping("/update/{spuId}")
    public R updateAttrBySpu(@PathVariable("spuId") Long spuId, @RequestBody List<ProductAttrValueEntity>
        attrValueEntities
    ){
        attrService.updateAttrBySpu(spuId, attrValueEntities);
        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		AttrResp attr = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attrVo){
		attrService.save(attrVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrResp attrResp){
		attrService.updateAttr(attrResp);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));
        return R.ok();
    }

}
