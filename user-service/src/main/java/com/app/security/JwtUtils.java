package com.app.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
	@Value("${SECRET_KEY}")
	private String jwtSecret;
	
	@Value("${EXP_TIMEOUT}")
	private int jwtExpirationMs;

	//invoked on successful authentication
	public String generateJwtToken(Authentication authentication) {
		log.info("generate JWT Token "+authentication);
		AppUserDetails userPrincipal=(AppUserDetails) authentication.getPrincipal();
		return Jwts.builder()								// JWTs: a Factory class, used to create JWT tokens
				.setSubject((userPrincipal.getUsername())) 	// setting subject of the token(typically user name):sets subject claim part of the token
				.setIssuedAt(new Date())					// Sets the JWT Claims (issued at) value of current date
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))	// Sets the JWT Claims expiration value.										
				.signWith(SignatureAlgorithm.HS512, jwtSecret)						// Signs the constructed JWT using the specified algorithm with the specified key, 
				.compact();															//producing a JWS(Json web signature=signed JWT)
															// Actually builds the JWT and serializes it to a compact, URL-safe string							
	}
	
	// this method will be invoked by our custom filter
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	// this method will be invoked by our custom filter
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().						// Returns a new JwtParser instance used to parse JWT strings.
					setSigningKey(jwtSecret).	// Sets the signing key used to verify JWT digital signature.
					parseClaimsJws(authToken);	// Parses the signed JWT returns the resulting Jws<Claims> instance
												// throws exc in case of failures in verification
			return true;
		} catch (Exception e) {
			log.error("Invalid JWT : " + e.getMessage());
		}

		return false;
	}
	
	public boolean destroyJWT(String token) {
		System.out.println(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).toString());
		return false;
	}
}
