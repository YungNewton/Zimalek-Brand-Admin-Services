package com.zmarket.brandadminservice.modules.security.jwt;

import com.zmarket.brandadminservice.modules.security.UserModelDetailsService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    private UserModelDetailsService userModelDetailsService;

    public JWTConfigurer(TokenProvider tokenProvider, UserModelDetailsService userModelDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userModelDetailsService = userModelDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) {
        JWTFilter customFilter = new JWTFilter(tokenProvider, userModelDetailsService);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
