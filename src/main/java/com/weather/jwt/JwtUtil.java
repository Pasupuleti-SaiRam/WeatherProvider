package com.weather.jwt;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	//we need secret key
	@Value("${app.secret}")
	private  String secret;
	
	//generate token
	// subject im using username
	public String generateToken(String subject) 
	{
		
		
		return Jwts.builder()
					
					.setSubject(subject)
					.setIssuer("sairam")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.HOURS.toMillis(24)))
					.signWith(SignatureAlgorithm.HS256,secret.getBytes())
					.compact();
		
	}
	
	//read claims
	public Claims getClaims(String token) {
		
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
		
	}
	
	//read token exp date from clams
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
		
	}
	//read subject/user which user trying to login
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}
	// validating if token is exp or not
	public boolean isTokenExp(String token) {
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	//validate username in token, expDate of token
	public boolean validateToken(String token,String username) {
		String tokenUserName = getUserName(token);
		return (username.equals(tokenUserName) && !isTokenExp(token));
	}
	
	

}
