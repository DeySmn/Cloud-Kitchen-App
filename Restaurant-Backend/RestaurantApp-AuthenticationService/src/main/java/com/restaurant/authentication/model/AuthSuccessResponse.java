package com.restaurant.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthSuccessResponse {
	private String email;
	private String token;
	private boolean isAuthenticated;
}
