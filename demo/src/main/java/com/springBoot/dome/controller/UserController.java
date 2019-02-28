package com.springBoot.dome.controller;



import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.dome.commom.Result;
import com.springBoot.dome.interceptor.LoginCheck;
import com.springBoot.dome.interceptor.Token;
import com.springBoot.dome.pojo.Dto;
import com.springBoot.dome.pojo.UserInfo;
import com.springBoot.dome.redis.TokenService;
import com.springBoot.dome.service.UserService;

@RestController
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userS;
	@Autowired
    private TokenService tokenService;
	
	@LoginCheck(check=true)
	@RequestMapping(value="/show/{id}", method=RequestMethod.GET)
	public Result showUserInfo (@PathVariable String id, HttpServletRequest request) {
		if (id == null || id.equals("")) {
			return Result.error(null, Result.ERROR_DATA_MISSING_MSG, Result.ERROR_DATA_MISSING);
		}
		UserInfo user = userS.selectUserInfoByUserId(Integer.valueOf(id));
		if (user == null) {
			return Result.error(null, Result.ERROR_NO_RESULT_MSG, Result.ERROR_NO_RESULT);
		}
		String token = null;
		if (request.getSession().getAttribute("token") != null) {
			token = (request.getSession().getAttribute("token")).toString();
		} else {
			token = tokenService.generateToken(request.getHeader("user-agent"), user.getAccount());
			request.getSession().setAttribute("token", token);
		}
		Dto dto = new Dto();
		dto.setTokenCreatedDate(System.currentTimeMillis());
        dto.setTokenExpiryDate(System.currentTimeMillis() + 30*60*1000);
        dto.setToken((request.getSession().getAttribute("token")).toString());
        dto.setIsLogin("true");
        user.setDto(dto);
        UserInfo userStr = (UserInfo)request.getSession().getAttribute("user");
        if (userStr != null) {
        	user.setToken(userStr.getToken());
        }
        
		return Result.success(user, Result.SUCCESS_MSG, Result.SUCCESS_STATUS);
	}
	@Token(remove=true)
	@RequestMapping(value="/insert")
	public Result insertUserInfo(UserInfo user) throws Exception{
		if (user == null || user.getName() == null || user.getName().equals("")) {
			return Result.error(null, Result.ERROR_DATA_MISSING_MSG, Result.ERROR_DATA_MISSING);
		}
		userS.insertUserInfo(user);
		return Result.success(null, Result.SUCCESS_MSG, Result.SUCCESS_STATUS);
	}
	
	@RequestMapping(value="/update")
	public Result updateUserInfo(UserInfo user) throws Exception{
		if (user == null || user.getUserId() == null) {
			return Result.error(null, Result.ERROR_DATA_MISSING_MSG, Result.ERROR_DATA_MISSING);
		}
		userS.updateUserInfoByUserId(user);
		return Result.success(null, Result.SUCCESS_MSG, Result.SUCCESS_STATUS);
	}
	
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public Result deleteUserInfo(@PathVariable String id) throws Exception{
		if (id == null || "".equals(id)) {
			return Result.error(null, "用户id不能为空", Result.ERROR_DATA_MISSING);
		}
		userS.deleteUserInfo(Integer.valueOf(id));
		return Result.success(null, Result.SUCCESS_MSG, Result.SUCCESS_STATUS);
	}
	@RequestMapping(value="/login/{account}/{password}", method=RequestMethod.GET)
	public Result login(@PathVariable String account, @PathVariable String password, HttpServletRequest request) throws Exception{
		UserInfo user = userS.login(account);
		Dto dto = new Dto();
		if (user != null) {
			if (user.getPassword().equals(password)) {
	            String token = tokenService.getUserToken(request.getHeader("user-agent"));
	            user.setToken(token);
	            request.getSession().setAttribute("user", user);
	            dto.setIsLogin("true");
	            user.setDto(dto);
				return Result.success(user, Result.SUCCESS_MSG, Result.SUCCESS_STATUS);
			} else {
				dto.setIsLogin("false");
				UserInfo us = new UserInfo();
				us.setDto(dto);
				return Result.success(us, "密码错误", Result.SUCCESS_STATUS);
			}
		} else {
			dto.setIsLogin("false");
			UserInfo us = new UserInfo();
			us.setDto(dto);
			return Result.success(us, "账号不存在", Result.SUCCESS_STATUS);
		}
		
	}
	
	public Result selectUserInfoList() {
		return null;
	}
}
