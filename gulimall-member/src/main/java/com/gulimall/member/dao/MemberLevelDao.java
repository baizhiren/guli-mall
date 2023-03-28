package com.gulimall.member.dao;

import com.gulimall.member.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级
 * 
 * @author chao
 * @email chao@gmail.com
 * @date 2023-03-27 19:57:09
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {
	
}
