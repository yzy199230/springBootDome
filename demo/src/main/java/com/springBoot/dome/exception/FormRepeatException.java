package com.springBoot.dome.exception;

public class FormRepeatException  extends Exception{

	private static final long serialVersionUID = 4632393192958086928L;

	public FormRepeatException(String message) { 
		super(message);
	}

    public FormRepeatException(String message, Throwable cause){ 
    	super(message, cause);
    }
}
