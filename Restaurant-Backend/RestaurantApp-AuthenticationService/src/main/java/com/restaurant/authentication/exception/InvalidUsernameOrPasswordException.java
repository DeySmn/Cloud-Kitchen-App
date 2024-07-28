package com.restaurant.authentication.exception;

public class InvalidUsernameOrPasswordException extends Exception {
	private String message;
	public InvalidUsernameOrPasswordException(String message){
		super(message);
		this.message =  message;
	}
}
