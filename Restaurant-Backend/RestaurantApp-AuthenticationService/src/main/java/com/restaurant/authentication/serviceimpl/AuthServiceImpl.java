package com.restaurant.authentication.serviceimpl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.restaurant.authentication.constants.SecurityConstants;
import com.restaurant.authentication.exception.UserNotFoundException;
import com.restaurant.authentication.model.AuthSuccessResponse;
import com.restaurant.authentication.model.ProfilePicture;
import com.restaurant.authentication.model.UserDetails;
import com.restaurant.authentication.repository.UserRepository;
import com.restaurant.authentication.service.AuthService;
import com.restaurant.authentication.utility.UniqueIdGeneratorService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UniqueIdGeneratorService uniqueIdGenerator;
	
	public String generateToken(UserDetails user) {
		SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
		String token = Jwts.builder().setIssuer("AMMA KI KADAI").setSubject("JWT Token")
				.claim("username", user.getName()).claim("authorities", "ROLE_SOCIAL_USER").setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + 30000000)) // 8 hrs
				.signWith(key).compact();
		return token;
	}

	@Override
	public boolean isUserExists(String email) {
		// TODO Auto-generated method stub
		Optional<UserDetails> user = userRepository.findByEmail(email);
		return user.isEmpty() ? false : true;
	}

	@Override
	public ResponseEntity<Object> getUserByEmailForSocialLogin(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		UserDetails user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User Not Found."));
		String token = generateToken(user);
		AuthSuccessResponse authResponse = new AuthSuccessResponse(user.getEmail(), token, true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Object> addNewUserViaFacebookSocialLogin(User user, ProfilePicture picture) {
		// TODO Auto-generated method stub
		UserDetails socialUser = new UserDetails();
		socialUser.setEmail(user.getId());
		socialUser.setName(user.getName());
		socialUser.setImg_url(picture.getUrl());
		String token = generateToken(socialUser);
		String userId = uniqueIdGenerator.generateUniqueUsedId(user.getName().split(" ")[0]);
		socialUser.setUserId(userId);
		userRepository.save(socialUser);
		AuthSuccessResponse authResponse = new AuthSuccessResponse(socialUser.getEmail(), token, true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> addNewUserViaGoogleSocialLogin(Payload payload) {
		// TODO Auto-generated method stub
		UserDetails socialUser = new UserDetails();
		socialUser.setEmail(payload.getEmail());
		socialUser.setName(payload.get("name").toString());
		socialUser.setImg_url(payload.get("picture").toString());
		String token = generateToken(socialUser);
		String userId = uniqueIdGenerator.generateUniqueUsedId(socialUser.getName().split(" ")[0]);
		socialUser.setUserId(userId);
		userRepository.save(socialUser);
		AuthSuccessResponse authResponse = new AuthSuccessResponse(socialUser.getEmail(), token, true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}

}
