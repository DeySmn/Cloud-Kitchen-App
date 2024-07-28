package com.restaurant.authentication.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.restaurant.authentication.constants.AuthenticationConstants;
import com.restaurant.authentication.constants.SecurityConstants;
import com.restaurant.authentication.exception.InvalidUsernameOrPasswordException;
import com.restaurant.authentication.exception.UserNotFoundException;
import com.restaurant.authentication.model.AuthSuccessResponse;
import com.restaurant.authentication.model.ProfilePicture;
import com.restaurant.authentication.model.SocialLoginToken;
import com.restaurant.authentication.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthService authService;
	

	@Value("${google.clientId}")
    private String googleClientId;
	
	@Value("${spring.social.facebook.appId}")
	private String fbClientId;
	
	@Value("${spring.social.facebook.appSecret}")
	private String fbClientToken;

	@GetMapping(value = AuthenticationConstants.VALIDATEAPPLOGIN_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> loginUser(Authentication user, HttpServletResponse response) throws UserNotFoundException, InvalidUsernameOrPasswordException{
		AuthSuccessResponse authResponse = new AuthSuccessResponse();
		authResponse.setEmail(user.getPrincipal().toString());
		authResponse.setToken(response.getHeader(SecurityConstants.JWT_HEADER));
		authResponse.setAuthenticated(true);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
	
	@PostMapping(value = AuthenticationConstants.VALIDATEGOOGLESOCIALLOGIN_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateSocialLogin(@RequestBody SocialLoginToken token) throws IOException, UserNotFoundException{
		log.info("inside user service to get all users");
		final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), token.getValue());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();        
        if(authService.isUserExists(payload.getEmail())) {
            return authService.getUserByEmailForSocialLogin(payload.getEmail());
        }
        else {
            return authService.addNewUserViaGoogleSocialLogin(payload);
        }
	}

	@PostMapping(value = AuthenticationConstants.VALIDATEFACEBOOKSOCIALLOGIN_URI, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateFbLogin(@PathVariable("id") String id, @RequestBody SocialLoginToken token) throws IOException, UserNotFoundException{
		log.info("inside FB service to get all users");
		FacebookConnectionFactory factory = new FacebookConnectionFactory(fbClientId, fbClientToken);
		OAuth2Operations operations = factory.getOAuthOperations();
		AccessGrant accessToken = new AccessGrant(token.getValue());
		Connection<Facebook> connection =factory.createConnection(accessToken);
		Facebook facebook = connection.getApi();
		String[] fields = {"id","name","picture"};
		User userProfile = facebook.fetchObject("me", User.class, fields);
		ObjectMapper mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String,Object> data = mapper.convertValue(userProfile.getExtraData().getOrDefault("picture",new LinkedHashMap<>()), LinkedHashMap.class);
		ProfilePicture picture = mapper.convertValue(data.getOrDefault("data",new ProfilePicture()), ProfilePicture.class);
		System.out.println(userProfile.getId());
		if(authService.isUserExists(userProfile.getId())) {
            return authService.getUserByEmailForSocialLogin(userProfile.getId());
        }
        else {
        	return authService.addNewUserViaFacebookSocialLogin(userProfile,picture);
        }
	}
	
}
