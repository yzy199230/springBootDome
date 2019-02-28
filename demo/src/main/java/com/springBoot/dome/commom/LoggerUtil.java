package com.springBoot.dome.commom;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.springBoot.dome.controller.UserController;

@Aspect
@Component
public class LoggerUtil {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Pointcut("execution(public * com.springBoot.dome.controller..*.*(..))")
    public void log() {
    }
	
	
	@Before("log()")
	public void doBefore() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOG.info("*********打印请求信息开始**********");
        LOG.info("URL : " + request.getRequestURL().toString());
        LOG.info("IP : " + request.getRemoteAddr());
        LOG.info("HTTP_METHOD : " + request.getMethod());
        LOG.info("*********打印请求信息结束**********");
	}
	
	
	@Around("execution(* com.springBoot.dome.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		result = proceedingJoinPoint.proceed();
        return result;
    }
	
	
	@AfterReturning(returning="object",pointcut="log()")
	public void resMessage(Object object) {
		LOG.info("*********打印结果信息开始**********");
		LOG.info("RESULT:"+ JSON.toJSONString(object));
		LOG.info("*********打印结果信息结束**********");
	}


}
