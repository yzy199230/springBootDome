package com.springBoot.dome.redis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder  implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		// TODO Auto-generated method stub
		 applicationContext = ctx;
	}

	public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
	
	public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
}
