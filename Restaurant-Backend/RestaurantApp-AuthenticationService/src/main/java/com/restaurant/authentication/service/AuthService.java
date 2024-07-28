package com.restaurant.authentication.service;

import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.User;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.restaurant.authentication.exception.UserNotFoundException;
import com.restaurant.authentication.model.ProfilePicture;

public interface AuthService {
	public boolean isUserExists(String email);
	public ResponseEntity<Object> getUserByEmailForSocialLogin(String email) throws UserNotFoundException;
	public ResponseEntity<Object> addNewUserViaGoogleSocialLogin(Payload payload);
	public ResponseEntity<Object> addNewUserViaFacebookSocialLogin(User user, ProfilePicture picture);
}
