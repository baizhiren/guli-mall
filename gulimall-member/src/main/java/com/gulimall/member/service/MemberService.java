package com.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.utils.PageUtils;
import com.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-27 19:57:09
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

