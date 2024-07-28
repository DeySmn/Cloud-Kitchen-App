package com.restaurant.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restaurant.authentication.model.CustomErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	CustomErrorResponse error;
	@ExceptionHandler(value = {UserNotFoundException.class})
	public ResponseEntity<Object> exception(UserNotFoundException exception){
		error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {InvalidUsernameOrPasswordException.class})
	public ResponseEntity<Object> exception(InvalidUsernameOrPasswordException exception){
		error = new CustomErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = {BadCredentialsException.class})
	public ResponseEntity<Object> exception(BadCredentialsException exception){
		error = new CustomErrorResponse(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class})
	public ResponseEntity<Object> exception(IllegalArgumentException exception){
		error = new CustomErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY, HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
	public ResponseEntity<Object> exception(HttpRequestMethodNotSupportedException exception){
		error = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> exception(Exception exception){
		error = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());
		return new ResponseEntity<Object>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}