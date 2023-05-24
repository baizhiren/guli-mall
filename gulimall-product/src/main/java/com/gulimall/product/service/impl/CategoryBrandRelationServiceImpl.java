package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.BrandService;
import com.gulimall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.gulimall.product.dao.CategoryBrandRelationDao;
import com.gulimall.product.entity.CategoryBrandRelationEntity;
import com.gulimall.product.service.CategoryBrandRelationService;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Resource
    CategoryService categoryService;

    @Resource
    BrandService brandService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();

        String brand_name = brandService.getById(brandId).getName();
        String category_name = categoryService.getById(catelogId).getName();

        categoryBrandRelation.setBrandName(brand_name);
        categoryBrandRelation.setCatelogName(category_name);

        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(BrandEntity brand) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();

        categoryBrandRelationEntity.setBrandName(brand.getName());
        categoryBrandRelationEntity.setBrandId(brand.getBrandId());
        UpdateWrapper<CategoryBrandRelationEntity> wrapper =
                new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brand.getBrandId());
        this.update(categoryBrandRelationEntity, wrapper);

    }

    @Override
    public void updateCategory(CategoryEntity category) {
        this.baseMapper.updateCategory(category.getCatId(), category.getName());
    }

    @Override
    public List<CategoryBrandRelationEntity> getCategoryBrand(Long catId) {
        List<CategoryBrandRelationEntity> relations = this.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        return relations;
    }
}