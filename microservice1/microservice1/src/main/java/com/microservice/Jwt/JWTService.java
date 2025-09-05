package com.microservice.Jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


@Service
public class JWTService {

	private final static String SECRET_KEY = "secretkey1234";
	private final static long EXPIRE_TIME = 86400000 ;
	
//	public String genrateToken(String username , String role) {
//		
//		return JWT.create()
//				.withSubject(username)
//		        .withClaim("role", role)
//		        .withIssuedAt(new Date())
//		        .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_TIME))
//		        .sign(Algorithm.HMAC256(SECRET_KEY));
//	}
//	
	
	public String ValidateTokenReturnUsername(String token) {
		
		return JWT.require(Algorithm.HMAC256(SECRET_KEY))
				.build()
				.verify(token)
				.getSubject();
	}


	
}
