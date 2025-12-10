package com.example.api_gateway.filter;

import com.example.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private final String[] publicEndpoints = {
            "/auth/login",
            "/auth/register"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { // filter() is the entry point of gateway,exchange is request-response class,chain filter route it to next filter

        String path = exchange.getRequest().getURI().getPath(); // checking request path

        // Allow public URLs
        for (String open : publicEndpoints) {
            if (path.startsWith(open)) {
                return chain.filter(exchange);
            }
        }

        // Check for Authorization header
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Extract token
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        String token = authHeader.replace("Bearer ", "");

        // Validate token
        if (!jwtUtil.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Token valid → continue forward
        return chain.filter(exchange); // Gateway ka request–response wrapper. if we do not use this request will not forward to microservices
    }
}
