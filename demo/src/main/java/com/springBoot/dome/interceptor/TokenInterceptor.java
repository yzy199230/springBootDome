package com.springBoot.dome.interceptor;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springBoot.dome.exception.TokenCheckException;
import com.springBoot.dome.pojo.UserInfo;
import com.springBoot.dome.redis.TokenService;


public class TokenInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(Token.class);
	@Autowired
    private TokenService tokenService;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LOG.info("~~~进入拦截器，开始验证token... ~~~");
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                	UserInfo userStr = (UserInfo)request.getSession().getAttribute("user");
                    request.getSession(false).setAttribute("token", tokenService.generateToken(request.getHeader("user-agent"), userStr.getAccount()));
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                    	LOG.warn("### please don't repeat submit,url:" + request.getServletPath() + " ###");
                    	request.getSession(false).removeAttribute("token");
                    	throw new TokenCheckException();
                    }
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
 
    private boolean isRepeatSubmit(HttpServletRequest request) {
    	String clinetToken = request.getParameter("token");
    	LOG.info("~~~ clinetToken:" + clinetToken + " ~~~");
		if (clinetToken == null) {
			return true;
	    }
		String serverToken = null;
		if (request.getSession().getAttribute("token") != null) {
			serverToken = (String) request.getSession(false).getAttribute("token");
		}
        LOG.info("~~~ serverToken:" + serverToken + " ~~~");
        if (serverToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }
}
