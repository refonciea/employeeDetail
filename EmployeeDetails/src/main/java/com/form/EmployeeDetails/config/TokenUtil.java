package com.form.EmployeeDetails.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class TokenUtil  {
	


	@Value("${secret.key}")
	private String secretkey;
	
	public static final long ExpirationTime = 5*60*60*1000;
	
	public String tokenUsernamne(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	public Date tokenExpirationDate(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = tokenClaims(token);
		return claimsResolver.apply(claims);
	}



	private Claims tokenClaims(String token) {
		return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
	}
	
	private Boolean validatteTokenExpired(String token) {
		final Date expiration = tokenExpirationDate(token);
		return expiration.before(new Date());
	}
	//token
	public String tokenGenerate(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
		
		
	}
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ExpirationTime ))
				.signWith(SignatureAlgorithm.HS512, secretkey).compact();
	}
	
		public Boolean checkvalidation(String token, UserDetails userDetails) {
			final String username = tokenUsernamne(token);
			return (username.equals(userDetails.getUsername()) && !validatteTokenExpired(token));
		}

}
