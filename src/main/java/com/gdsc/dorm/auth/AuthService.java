package com.gdsc.dorm.auth;

import com.gdsc.dorm.auth.data.dto.req.SignUpReq;
import com.gdsc.dorm.member.MemberRepository;
import com.gdsc.dorm.member.data.Member;
import com.gdsc.dorm.member.data.dto.res.MemberGetRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder pwEncoder;

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
}
