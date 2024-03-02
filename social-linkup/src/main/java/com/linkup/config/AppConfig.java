package com.linkup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;


import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						Authorize -> Authorize.antMatchers("/api/**").authenticated().anyRequest().permitAll())
				.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class).csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource((org.springframework.web.cors.CorsConfigurationSource) corsConfigurationSource()));

		return http.build();
	}

	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);
				return cfg;
			}
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}