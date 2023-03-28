package com.gulimall.product;

import com.gulimall.product.entity.BrandEntity;
import com.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallProductApplicationTests {
    @Autowired
    BrandService brandService;



    @Test
    void contextLoads() {
    }

    @Test
    void testSaveBrand(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setDescript("华为");
        boolean save = brandService.save(brandEntity);
        System.out.println(save);
    }



}
