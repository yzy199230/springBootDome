package com.springBoot.dome.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.pagehelper.PageHelper;
import com.springBoot.dome.interceptor.LoginCheckInterceptor;
import com.springBoot.dome.interceptor.TokenInterceptor;

@Configuration
@ComponentScan(useDefaultFilters = true)
public class WebAppConfig implements WebMvcConfigurer{
	

	/** 
     * 配置拦截器 
     * @author lance 
     * @param registry 
     */  
    public void addInterceptors(InterceptorRegistry registry) {  
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/**");
        // registry.addInterceptor(getSessionInterceptor()).addPathPatterns("/**").excludePathPatterns("/show");
    }
    
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        /*是否允许请求带有验证信息*/
        corsConfiguration.setAllowCredentials(true);
        /*允许访问的客户端域名*/
        corsConfiguration.addAllowedOrigin("*");
        /*允许服务端访问的客户端请求头*/
        corsConfiguration.addAllowedHeader("*");
        /*允许访问的方法名,GET POST等*/
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
    
    //配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper(){
	    PageHelper pageHelper = new PageHelper();
	    Properties properties = new Properties();
	    properties.setProperty("offsetAsPageNum","true");
	    properties.setProperty("rowBoundsWithCount","true");
	    properties.setProperty("reasonable","true");
	    properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
	    pageHelper.setProperties(properties);
	    return pageHelper;
	}

}
