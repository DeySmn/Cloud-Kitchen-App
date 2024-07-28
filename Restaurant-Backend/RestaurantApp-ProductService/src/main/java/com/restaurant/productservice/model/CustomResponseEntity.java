package com.restaurant.productservice.model;

import java.io.Serializable;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseEntity<T> implements Serializable  {

	/**
	 * 
	 */

	private T body;
	private HttpStatusCode status;

	public CustomResponseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomResponseEntity(T body, HttpStatusCode status) {
		this.body = body;
		this.status = status;
		// TODO Auto-generated constructor stub
	}

}
