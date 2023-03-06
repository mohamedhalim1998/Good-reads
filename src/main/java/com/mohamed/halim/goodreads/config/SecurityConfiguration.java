package com.mohamed.halim.goodreads.config;

import com.mohamed.halim.goodreads.security.JwtAuthFilter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfiguration {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
       return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .pathMatchers("/api/v1/auth/**").permitAll()
                .pathMatchers("/h2-console**").permitAll()
                .anyExchange().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> {
                    log.info("[1] Authentication error: Unauthorized[401]: " + e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
                })
                .accessDeniedHandler((swe, e) -> {
                    log.info("[2] Authentication error: Access Denied[401]: " + e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
                })
                .and()
                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .httpBasic().disable()
               .build();

    }

}
