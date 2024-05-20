package com.gdsc.dorm.config.jwt;

import static org.assertj.core.api.Assertions.*;

import com.gdsc.dorm.member.MemberRepository;
import com.gdsc.dorm.member.data.Member;
import com.gdsc.dorm.member.type.Dorm;
import com.gdsc.dorm.member.type.Gender;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void generateToken() {
        //given
        Member member = memberRepository.save(Member.builder()
                .name("testMember")
                .email("test@email.com")
                .password("test")
                .gender(Gender.MALE)
                .dorm(Dorm.BONGSA)
                .build());

        //when
        String token = tokenProvider.generateToken(member, Duration.ofDays(14));

        //then
        Long memberId = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    void validToken_invalid() {
        //given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis())) // 만료 기한을 7일 전으로 설정
                .build()
                .createToken(jwtProperties);

        //when
        boolean result = tokenProvider.validToken(token);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void validToken_valid() {
        //given
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        //when
        boolean result = tokenProvider.validToken(token);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void getAuthentication() {
        //given
        String memberEmail = "testMember@email.com";
        String token = JwtFactory.builder()
                .subject(memberEmail)
                .build()
                .createToken(jwtProperties);

        //when
        Authentication authentication = tokenProvider.getAuthentication(token);

        //then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(memberEmail);
    }

    @Test
    void getMemberrId() {
        //given
        Long memberId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", memberId))
                .build()
                .createToken(jwtProperties);

        //when
        Long memberIdByToken = tokenProvider.getUserId(token);

        //then
        assertThat(memberIdByToken).isEqualTo(memberId);
    }
}
