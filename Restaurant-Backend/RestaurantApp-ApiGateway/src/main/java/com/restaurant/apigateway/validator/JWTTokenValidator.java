package com.restaurant.apigateway.validator;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.restaurant.apigateway.constants.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.netty.util.internal.StringUtil;

@Component
public class JWTTokenValidator {

    public boolean isTokenValid(String jwt){
        Claims claims = getAllClaims(jwt);
        if(null != claims) {
	        String username = String.valueOf(claims.get("username"));
	        String authorities = (String) claims.get("authorities");
	        System.out.println("Username - " + username);
	        System.out.println("Authorities - " + authorities);
	        return !claims.getExpiration().before(new Date());
        }
        return false;
    }
    
    public Claims getAllClaims(String token) {
    	SecretKey key = null;
    	try {
    		if(!StringUtil.isNullOrEmpty(token)) {
    			token = token.substring(7);
    	    	key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
    	    	return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    		}
    	}catch (Exception e) {
    		System.out.println("Invalid Authorization Token");
		}
    	return null;
    }
	
}
