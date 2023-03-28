package com.gulimall.coupon;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootTest
class GulimallCouponApplicationTests {
    @Value("${coupon.user.name}")
    String username;

    @Value("${coupon.user.age}")
    int age;

    @Test
    void contextLoads() {
        System.out.println("username:" + username + " age:" + age);
    }

}
