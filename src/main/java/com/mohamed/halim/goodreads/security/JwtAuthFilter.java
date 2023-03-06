package com.mohamed.halim.goodreads.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@AllArgsConstructor
public class JwtAuthFilter implements WebFilter {

    public static final String HEADER_PREFIX = "Bearer ";
    private final JwtService tokenProvider;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange);
        if (StringUtils.hasText(token) && this.tokenProvider.isTokenValid(token)) {
            String username = tokenProvider.extractUsername(token);
            Mono<Authentication> authentication = userDetailsService.findByUsername(username).map(userDetails ->
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), List.of()));
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(authentication.map(SecurityContextImpl::new)))
                    .switchIfEmpty(chain.filter(exchange));
        }
        return chain.filter(exchange);
    }


    private String resolveToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return  token != null ? token.substring(HEADER_PREFIX.length()) : null;
    }

}
