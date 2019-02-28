package com.springBoot.dome.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springBoot.dome.commom.Result;
import com.springBoot.dome.exception.LoginCheckException;
import com.springBoot.dome.exception.TokenCheckException;

@RestControllerAdvice
public class GlobalExceptionHandler {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
     
    
    @ExceptionHandler(TokenCheckException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handlerTokenCheckException(TokenCheckException ex) {
    	LOGGER.error("request Exception:", ex);
    	return Result.error(null, Result.ERROR_CHECK_TOKEN_MSG, Result.ERROR_CHECK_TOKEN);
    }
    
    @ExceptionHandler(LoginCheckException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handlerLoginCheckException(LoginCheckException ex) {
    	LOGGER.error("request Exception:", ex);
    	return Result.error(null, Result.ERROR_NEED_LOGIN_MSG, Result.ERROR_NEED_LOGIN);
    }
    
    
    
    /**
     * 特别说明： 可以配置指定的异常处理,这里处理所有
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(HttpServletRequest request, Exception e) {
        LOGGER.error("request Exception:", e);
        return Result.error(null, Result.ERROR_SYSTEM_MSG, Result.ERROR_SYSTEM);
    }
 
}
