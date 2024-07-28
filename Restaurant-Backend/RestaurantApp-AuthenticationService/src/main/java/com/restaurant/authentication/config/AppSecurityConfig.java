package com.restaurant.authentication.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.restaurant.authentication.filter.JWTAccessDeniedHandler;
import com.restaurant.authentication.filter.JWTAuthenticationEntryPoint;
import com.restaurant.authentication.filter.JWTTokenGeneratorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppSecurityConfig {

	@Autowired
	JWTAuthenticationEntryPoint entryPoint;

	@Autowired
	JWTAccessDeniedHandler accessDeniedHandler;

	@Bean
	CorsConfigurationSource getCorsConfigurationSource() {

		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
//		        config.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
				config.setAllowedOrigins(Arrays.asList("https://amma-ki-kadai.vercel.app",
						"https://amma-ki-kadai-admin.vercel.app", 
						"http://localhost:4200",
						"https://localhost:4200",
						"https://localhost:4201",
						"https://localhost:4201"));
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
				.csrf((csrf) -> csrf.ignoringRequestMatchers("/api/v1/authservice/auth/google/login", 
						"/api/v1/authservice/auth/facebook/**"))
				.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.authorizeHttpRequests((request)->request
						.requestMatchers("/api/v1/authservice/auth/google/login", 
								"/api/v1/authservice/auth/facebook/**","/actuator/**").permitAll())
				.httpBasic(Customizer.withDefaults()).formLogin().loginProcessingUrl("/api/v1/authservice/auth/user/login").permitAll();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
