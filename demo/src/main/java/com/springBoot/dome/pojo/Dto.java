package com.springBoot.dome.pojo;

import java.io.Serializable;

public class Dto implements Serializable{
 
	private static final long serialVersionUID = 474065009538258289L;

	private String token;
 
    private Long tokenCreatedDate;
 
    private Long tokenExpiryDate;
 
    private String isLogin;
 
    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        this.token = token;
    }
 
    public Long getTokenCreatedDate() {
        return tokenCreatedDate;
    }
 
    public void setTokenCreatedDate(Long tokenCreatedDate) {
        this.tokenCreatedDate = tokenCreatedDate;
    }
 
    public Long getTokenExpiryDate() {
        return tokenExpiryDate;
    }
 
    public void setTokenExpiryDate(Long tokenExpiryDate) {
        this.tokenExpiryDate = tokenExpiryDate;
    }
 
    public String getIsLogin() {
        return isLogin;
    }
 
    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }
}
