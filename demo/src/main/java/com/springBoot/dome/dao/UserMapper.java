package com.springBoot.dome.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.springBoot.dome.pojo.UserInfo;
@CacheNamespace(implementation=(com.springBoot.dome.redis.RedisCache.class))
@CacheConfig(cacheNames = "userInfo")
@Mapper
public interface UserMapper {

	@Cacheable(key ="#p0") 
	public UserInfo selectUserInfoByUserId(Integer userId);
	
	public List<UserInfo> selectUserInfoList();
	
	@CachePut(key = "#p0")
	public void updateUserInfoByUserId(UserInfo user) throws Exception;
	
	public void insertUserInfo(UserInfo user) throws Exception;
	
	@CacheEvict(key ="#p0",allEntries=true)
	public void deleteUserInfo(Integer userId) throws Exception;
	
	public UserInfo login(String account) throws Exception;
	
	
}
