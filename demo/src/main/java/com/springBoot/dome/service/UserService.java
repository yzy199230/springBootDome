package com.springBoot.dome.service;

import java.util.List;

import com.springBoot.dome.pojo.UserInfo;

public interface UserService {

	public UserInfo selectUserInfoByUserId(Integer userId);
	
	public void updateUserInfoByUserId(UserInfo user) throws Exception;
	
	public void insertUserInfo(UserInfo user) throws Exception;
	
	public void deleteUserInfo(Integer userId) throws Exception;
	
	public UserInfo login(String account) throws Exception;
	
	public List<UserInfo> selectUserInfoList();
}
