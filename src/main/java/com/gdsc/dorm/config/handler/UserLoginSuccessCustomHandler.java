package com.gdsc.dorm.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.dorm.auth.data.dto.res.LoginRes;
import com.gdsc.dorm.config.jwt.TokenProvider;
import com.gdsc.dorm.jwt.RefreshTokenRepository;
import com.gdsc.dorm.jwt.data.RefreshToken;
import com.gdsc.dorm.member.data.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLoginSuccessCustomHandler implements AuthenticationSuccessHandler {
    //인증 성공 시 호출되는 핸들러

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Member loginMember = (Member) authentication.getPrincipal();

        String accessToken = tokenProvider.generateToken(loginMember, "access");
        String refreshToken = tokenProvider.generateToken(loginMember, "refresh");

        RefreshToken refreshTokenEntity = new RefreshToken(loginMember.getId(), refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        LoginRes loginRes = LoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        String res = objectMapper.writeValueAsString(loginRes);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(res);
    }
}
