package com.linkup.config;

import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

	private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public static String generateToken(Authentication auth) {
		
		
		
		String jwt = Jwts.builder().setIssuer("Mahaveer").setIssuedAt(new Date(0))
				.setExpiration(new Date(new Date(0).getTime() + 86400000))
				.claim("email", auth.getName())
				.signWith(key)
				.compact();

		return jwt;
	}

	public static String getEmailFromJwtToken(String jwt) {
		// Bearer Token
		jwt = jwt.substring(7);

		@SuppressWarnings("deprecation")
		Claims claims = Jwts.parser().setSigningKey(key) // Corrected method name
				.build().parseClaimsJws(jwt).getBody();

		String email = String.valueOf(claims.get("email"));

		return email;
	}
}

