package com.springBoot.dome.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springBoot.dome.dao.UserMapper;
import com.springBoot.dome.pojo.UserInfo;
import com.springBoot.dome.service.UserService;

@ComponentScan({"com.springBoot.demo.dao"})
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper um;
	
	@Override
	public UserInfo selectUserInfoByUserId(Integer userId) {
		// TODO Auto-generated method stub
		UserInfo user = um.selectUserInfoByUserId(userId);
		return user;
	}

	@Override
	@Transactional
	public void updateUserInfoByUserId(UserInfo user) throws Exception{
		// TODO Auto-generated method stub
		um.updateUserInfoByUserId(user);
	}

	@Override
	@Transactional
	public void insertUserInfo(UserInfo user) throws Exception{
		// TODO Auto-generated method stub
		um.insertUserInfo(user);
	}

	@Override
	@Transactional
	public void deleteUserInfo(Integer userId) throws Exception{
		// TODO Auto-generated method stub
		um.deleteUserInfo(userId);
	}

	@Override
	public UserInfo login(String account) throws Exception {
		// TODO Auto-generated method stub
		UserInfo user = um.login(account);
		return user;
	}

	public List<UserInfo> selectUserInfoList() {
		List<UserInfo> l = um.selectUserInfoList();
		return l;
	}
	
}
