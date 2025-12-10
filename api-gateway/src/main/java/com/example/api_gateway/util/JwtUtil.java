package com.example.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Component
public class JwtUtil {
    private final Key key = hmacShaKeyFor("mysecretmysecretmysecretmysecret".getBytes()); // converts string into ccryptographic key
    public Claims extractClaims(String token){  // extract  claims from the token like -username and password,issue and expiry date
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) //parse token (throws exception if invalid)
                .getBody();
    }
    public boolean isTokenValid(String token){ // checking token is valid or not
        try{
            extractClaims(token); // if this fails means token is invalid
            return true;
        } catch (Exception e) {
            return  false;
        }

    }

}
