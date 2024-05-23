package com.gdsc.dorm.auth;

import com.gdsc.dorm.auth.data.dto.req.LoginReq;
import com.gdsc.dorm.auth.data.dto.req.SignUpReq;
import com.gdsc.dorm.auth.data.dto.res.LoginRes;
import com.gdsc.dorm.config.jwt.TokenProvider;
import com.gdsc.dorm.jwt.RefreshTokenRepository;
import com.gdsc.dorm.jwt.data.RefreshToken;
import com.gdsc.dorm.member.MemberRepository;
import com.gdsc.dorm.member.data.Member;
import com.gdsc.dorm.member.data.dto.res.MemberGetRes;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BCryptPasswordEncoder pwEncoder;
    private final TokenProvider tokenProvider;

    public ResponseEntity<MemberGetRes> signUp(SignUpReq req) {
        if(req == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
        if(memberRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        // 추후 예외처리할 것
        String bcryptPw = pwEncoder.encode(req.getPassword());
        Member newMember = Member.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(bcryptPw)
                .gender(req.getGender())
                .dorm(req.getDorm())
                .build();

        memberRepository.save(newMember);

        return new ResponseEntity<>(new MemberGetRes(newMember), HttpStatus.CREATED);
    }

    public ResponseEntity<LoginRes> login(LoginReq req) {
        if(req == null) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        Member loginMember = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if(!pwEncoder.matches(req.getPassword(), loginMember.getPassword())) {
            throw new IllegalArgumentException("비밀번호를 틀렸습니다.");
        }

        String accessToken = tokenProvider.generateToken(loginMember, Duration.ofDays(1));
        String refreshToken = tokenProvider.generateToken(loginMember, Duration.ofDays(7));

        RefreshToken refreshTokenEntity = new RefreshToken(loginMember.getId(), refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);

        LoginRes res = LoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
