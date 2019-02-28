package com.springBoot.dome.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springBoot.dome.exception.LoginCheckException;
import com.springBoot.dome.pojo.UserInfo;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(Token.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LOG.info("~~~进入拦截器，开始验证登录状态... ~~~");
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginCheck annotation = method.getAnnotation(LoginCheck.class);
            if (annotation != null) {
                boolean needCheckLogin = annotation.check();
                if (needCheckLogin) {
                    if (isLogin(request)) {
                    	LOG.warn("### please login,url:" + request.getServletPath() + " ###");
                    	throw new LoginCheckException();
                    }
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
	
	private boolean isLogin(HttpServletRequest request) {
		UserInfo userStr = (UserInfo)request.getSession().getAttribute("user");
		if (userStr == null) {
			return true;
		}
		return false;
    }
}
