package com.restaurant.userservice.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.restaurant.userservice.filter.JWTTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppSecurityConfig {

	@Bean
	CorsConfigurationSource getCorsConfigurationSource() {

		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
//		        config.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
				config.setAllowedOrigins(Arrays.asList("https://amma-ki-kadai.vercel.app","https://amma-ki-kadai-admin.vercel.app", "http://localhost:4200","https://localhost:4200"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setExposedHeaders(Arrays.asList("Authorization"));
				config.setMaxAge(3600L);
				return config;
			}
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors()
				.configurationSource(getCorsConfigurationSource()).and()
				.csrf((csrf) -> csrf.ignoringRequestMatchers("/api/v1/userservice/auth/user/register",
						"/api/v1/userservice/verify/**","/api/v1/userservice/public/user/**"))
				.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class).exceptionHandling().and()
				.authorizeHttpRequests((request)->request
						.requestMatchers("/api/v1/userservice/verify/**").hasAnyRole("ADMIN","CUSTOMER","SOCIAL_USER")
				.requestMatchers("/api/v1/userservice/auth/user/register","/api/v1/userservice/public/user/**","/actuator/**").permitAll());
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
