package com.restaurant.authentication.config;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.restaurant.authentication.model.UserDetails;
import com.restaurant.authentication.repository.UserRepository;
@Component
public class AppAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Inside AppAuthenticationProvider");
		String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Optional<UserDetails> customer = userRepository.findByEmail(username);
        System.out.println(customer.isPresent()+"-"+username+"-"+pwd);
        if(customer.isPresent()){
            if(passwordEncoder.matches(pwd,customer.get().getPassword())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                if(null!=customer.get().getRole() && !customer.get().getRole().isEmpty()) {
                	customer.get().getRole().forEach((role)-> authorities.add(new SimpleGrantedAuthority(role)));                	
                }
                Authentication auth =  new UsernamePasswordAuthenticationToken(username,pwd,authorities);
                System.out.println("Inside auth");
                return auth;
            } else{
                throw new BadCredentialsException("Invalid password!");
            }
        } else{
            throw new BadCredentialsException("No user registered with this details!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
