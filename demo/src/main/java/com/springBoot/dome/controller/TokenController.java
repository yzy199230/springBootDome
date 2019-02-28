package com.springBoot.dome.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.dome.pojo.UserInfo;
import com.springBoot.dome.redis.TokenService;

@RestController
@RequestMapping("/token/")
public class TokenController {

	@Autowired
    private TokenService tokenService;
	
    @RequestMapping("/getToken")
    public String getToken(HttpServletRequest request, HttpServletResponse response){
		String userAgent = request.getHeader("user-agent");
		UserInfo user = (UserInfo)request.getSession().getAttribute("user");
		String token = tokenService.generateToken(userAgent, user.getAccount());
        request.getSession().setAttribute("token", token);
        return (String) request.getSession().getAttribute("token");
    }
}
