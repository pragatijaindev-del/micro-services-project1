package com.example.api_gateway.filter;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.api_gateway.util.JwtUtil;

import reactor.core.publisher.Mono;

//Global JWT authentication filter for API GatewayValidates JWT tokens before routing requests to downstream microservices.
//main work token present or not,valid or not

@Component
public class JwtAuthFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // Public APIs – no JWT required
    private final String[] publicEndpoints = {
            "/auth/login",
            "/auth/register"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Get request path
        String path = exchange.getRequest().getURI().getPath();

        //  Allow public endpoints directly
        for (String open : publicEndpoints) {
            if (path.startsWith(open)) {
                return chain.filter(exchange);
            }
        }

        // Read Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // If header missing  →  send 401
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //  Extract  ,size is 7
        String token = authHeader.substring(7);

        //  Validate token safely,otherwise request will stop here
        try {
            if (!jwtUtil.isTokenValid(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } catch (Exception e) {
            // Any JWT exception → 401 (like -expired token,malformed token etc)
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // if Token valid, forward request to respective microservice
        return chain.filter(exchange);
    }
}