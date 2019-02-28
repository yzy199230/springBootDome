package com.springBoot.dome.exception;

public class LoginCheckException extends RuntimeException{

	private static final long serialVersionUID = -7796731263725735338L;
	private static final String NEED_LOGIN = "20";
	
	public LoginCheckException() {
		super(NEED_LOGIN);
	}

}
