package com.gdsc.dorm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.dorm.auth.data.dto.req.LoginReq;
import com.gdsc.dorm.config.handler.UserLoginFailureCustomHandler;
import com.gdsc.dorm.config.handler.UserLoginSuccessCustomHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class UsernamePasswordAuthenticationCustomFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/auth/login",
            "POST");

    public UsernamePasswordAuthenticationCustomFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper,
                                                      UserLoginSuccessCustomHandler userLoginSuccessCustomHandler,
                                                      UserLoginFailureCustomHandler userLoginFailureCustomHandler) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.objectMapper = objectMapper;
        setAuthenticationSuccessHandler(userLoginSuccessCustomHandler);
        setAuthenticationFailureHandler(userLoginFailureCustomHandler);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //request body에서 id, pw 가져옴
        LoginReq req = null;
        try {
            req = objectMapper.readValue(request.getInputStream(), LoginReq.class);
        } catch(IOException e) {
            throw new RuntimeException("Internal Server Error");
        }

        //id, pw 기반으로 Auth Token 생성
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());

        //인증
        //내부적으로 loadByUserName 실행 후 (사용자가 존재하는지)
        //additionalAuthenticationChecks 호출 (비밀번호가 맞는지)
        Authentication authentication = this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

        return authentication;
    }
}
