package com.springBoot.dome.pojo;

import java.io.Serializable;

public class UserInfo implements Serializable{

	private static final long serialVersionUID = -8366929034564774130L;
	private Integer userId;
	private String name;
	private Integer age;
	private String account;
	private String password;
	private String token;
	private Dto dto;
	
	
	public Dto getDto() {
		return dto;
	}
	public void setDto(Dto dto) {
		this.dto = dto;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
