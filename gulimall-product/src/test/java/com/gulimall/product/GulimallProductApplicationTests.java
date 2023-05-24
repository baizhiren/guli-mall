package com.gulimall.product;


import com.alibaba.fastjson.support.hsf.HSFJSONUtils;
import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;

import com.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GulimallProductApplicationTests {
    @Autowired
    BrandService brandService;

//    @Test
//    void testSaveBrand(){
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setDescript("华为");
//        boolean save = brandService.save(brandEntity);
//        System.out.println(save);
//    }

    @Autowired
    CategoryService categoryService;
    @Test
    public void testCategoryPath(){
        Long[] pathByCategoryId = categoryService.getPathByCategoryId(225l);
        for(Long x: pathByCategoryId){
            log.info(x.toString());
        }

    }




}
