package com.restaurant.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfilePicture {
	private Long height;
	private String url;
	private Long width;
}
