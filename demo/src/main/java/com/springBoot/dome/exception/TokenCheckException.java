package com.springBoot.dome.exception;

public class TokenCheckException extends RuntimeException{

	private static final long serialVersionUID = -5519743897907627214L;
	private static final String TOKEN_STATUS = "999";
	
	public TokenCheckException() {
		super(TOKEN_STATUS);
	}
}
