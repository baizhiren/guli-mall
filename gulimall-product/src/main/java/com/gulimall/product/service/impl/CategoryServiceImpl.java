package com.gulimall.product.service.impl;

import com.sun.org.apache.xml.internal.resolver.CatalogEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.utils.PageUtils;
import com.common.utils.Query;

import com.gulimall.product.dao.CategoryDao;
import com.gulimall.product.entity.CategoryEntity;
import com.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> tree() {
        List<CategoryEntity> categories= baseMapper.selectList(null);

        return
        categories.stream().filter((menu) -> menu.getParentCid() == 0)
                .map(menu -> this.setChildren(menu, categories)).
                sorted((m1, m2) -> {
                    int s1 = m1.getSort() == null ? 0 : m1.getSort();
                    int s2 = m2.getSort() == null ? 0 : m2.getSort();
                    return s1 - s2;
                }).collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //todo 依赖项的管理
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] getPathByCategoryId(Long catelogId) {
        List<Long> path = new ArrayList<>();
        findPath(catelogId, path);
        return path.toArray(new Long[0]);
    }
    public void findPath(Long catelogId, List<Long> path){
        CategoryEntity categoryEntity = baseMapper.selectById(catelogId);
        Long parentCid = categoryEntity.getParentCid();
        if(parentCid != 0){
            findPath(parentCid, path);
        }
        path.add(catelogId);
    }

    public CategoryEntity setChildren(CategoryEntity parent, List<CategoryEntity> all){
               parent.setChildren(all.stream().filter((menu) -> menu.getParentCid() == parent.getCatId())
                        .map(menu -> this.setChildren(menu, all)).
                        sorted((m1, m2) -> {
                            int s1 = m1.getSort() == null ? 0 : m1.getSort();
                            int s2 = m2.getSort() == null ? 0 : m2.getSort();
                            return s1 - s2;
                        }).collect(Collectors.toList()));
                return parent;
    }


}