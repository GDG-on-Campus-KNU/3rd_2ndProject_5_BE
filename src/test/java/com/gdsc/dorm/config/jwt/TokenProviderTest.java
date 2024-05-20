package com.gdsc.dorm.config.jwt;

import com.gdsc.dorm.member.MemberRepository;
import com.gdsc.dorm.member.data.Member;
import com.gdsc.dorm.member.type.Dorm;
import com.gdsc.dorm.member.type.Gender;
import io.jsonwebtoken.Jwts;
import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        Assertions.assertThat(memberId).isEqualTo(member.getId());
    }
}
