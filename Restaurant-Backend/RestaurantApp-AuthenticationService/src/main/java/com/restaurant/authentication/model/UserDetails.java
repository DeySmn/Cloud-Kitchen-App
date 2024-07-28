package com.restaurant.authentication.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserDetails {
	@Id
	@Field("_id")
	private String userId;
	private String email;
	private String name;
	private String password;
	private String img_url;
	private List<String> role = new ArrayList<String> ();
}
