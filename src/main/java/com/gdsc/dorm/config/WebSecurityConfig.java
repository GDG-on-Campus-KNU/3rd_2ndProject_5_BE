package com.gdsc.dorm.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

//import com.gdsc.dorm.auth.LoginFailureHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.dorm.config.handler.UserLoginFailureCustomHandler;
import com.gdsc.dorm.config.handler.UserLoginSuccessCustomHandler;
import com.gdsc.dorm.config.jwt.TokenProvider;
import com.sun.net.httpserver.HttpsServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig   {
    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;
    private final UserLoginSuccessCustomHandler successHandler;
    private final UserLoginFailureCustomHandler failureHandler;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            //.requestMatchers("/login", "/api/**").permitAll()
                            .anyRequest().permitAll();
                })
                .formLogin((formLogin) -> {
                    formLogin.disable();
                })
                .with(new customFilter(), AbstractHttpConfigurer::getClass)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class customFilter extends AbstractHttpConfigurer<customFilter, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            http.addFilterBefore(new TokenAuthenticationFilter(tokenProvider),
                    UsernamePasswordAuthenticationFilter.class);
            http.addFilterAt(new UsernamePasswordAuthenticationCustomFilter(authenticationManager, objectMapper,successHandler, failureHandler)
                    ,UsernamePasswordAuthenticationFilter.class);

        }
    }
}
