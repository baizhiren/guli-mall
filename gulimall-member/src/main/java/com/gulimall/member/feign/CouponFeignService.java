package com.gulimall.member.feign;

import com.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("coupon")
public interface CouponFeignService {
    @RequestMapping("coupon/coupon/member")
    public R getMember();
}
