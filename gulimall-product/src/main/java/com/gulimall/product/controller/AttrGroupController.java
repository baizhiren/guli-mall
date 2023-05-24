package com.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.service.CategoryService;
import com.gulimall.product.vo.AttrGroupResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gulimall.product.entity.AttrGroupEntity;
import com.gulimall.product.service.AttrGroupService;
import com.common.utils.PageUtils;
import com.common.utils.R;
import com.gulimall.product.vo.GroupAttrRelation;


/**
 * 属性分组
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-23 11:30:09
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("categoryId") Long categoryId){
        PageUtils page = attrGroupService.queryPage(params, categoryId);
        return R.ok().put("page", page);
    }

    @GetMapping("/{attrgroupId}/attr/relation")
    public R getAttrsByGroup(@PathVariable("attrgroupId") Long attrGroupId){
        List<AttrEntity> list = attrGroupService.getAttrsByGroup(attrGroupId);
        return R.ok().put("data", list);
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody GroupAttrRelation relation[]){
        attrGroupService.deleteRelation(relation);
        return R.ok();
    }


    @GetMapping("/{attrgroupId}/noattr/relation")
    public R getNoAttrRelation(@PathVariable("attrgroupId") Long attrGroupId, @RequestParam Map<String, Object> params){
        PageUtils page = attrGroupService.getNoAttrRelation(params, attrGroupId);
        return R.ok().put("page", page);
    }






    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] catelogPath = categoryService.getPathByCategoryId(catelogId);
        attrGroup.setCatelogPath(catelogPath);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);
        return R.ok();
    }

    @PostMapping("/attr/relation")
    public R save(@RequestBody List<AttrAttrgroupRelationEntity> relationEntityList){
        attrGroupService.saveRelation(relationEntityList);
        return R.ok();
    }

    @GetMapping("/{catelogId}/withattr")
    public R getAttrsByCategory(@PathVariable("catelogId") Long cateLogId){
        List<AttrGroupResp> attrGroupResps = attrGroupService.getAttrsByCategory(cateLogId);
        return R.ok().put("data", attrGroupResps);
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
