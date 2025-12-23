package com.example.api_gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
//writing common bussiness logic here ,only validate and throw exception do,clear sepeation of concern
@Component // telling iOC to keep the bean of this class
public class JwtUtil {

    private static final String SECRET =
            "mysecretmysecretmysecretmysecret"; // jwt signing secret key

    private final Key key =
            Keys.hmacShaKeyFor(SECRET.getBytes()); // concerts string to cryptographic Key

    // Validate JWT token and secret key matching key should be same
    public boolean isTokenValid(String token) {
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);// token valid and structured
        return true;
    }
}