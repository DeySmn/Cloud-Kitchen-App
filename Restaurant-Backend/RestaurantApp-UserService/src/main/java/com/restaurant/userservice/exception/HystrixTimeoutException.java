package com.restaurant.userservice.exception;

public class HystrixTimeoutException extends Exception {
	private String message;
	public HystrixTimeoutException(String message){
		super(message);
		this.message =  message;
	}
}
