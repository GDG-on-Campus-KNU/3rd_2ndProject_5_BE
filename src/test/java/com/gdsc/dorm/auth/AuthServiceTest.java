package com.gdsc.dorm.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.dorm.auth.data.dto.req.SignUpReq;
import com.gdsc.dorm.member.MemberRepository;
import com.gdsc.dorm.member.data.Member;
import com.gdsc.dorm.member.type.Dorm;
import com.gdsc.dorm.member.type.Gender;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthServiceTest {
    @Autowired
    AuthService authService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void signUp() throws Exception {
        //given

        String name = "testUser";
        String email = "test@mail.com";
        String password = "testPw";
        Gender gender = Gender.MALE;
        Dorm dorm = Dorm.BONGSA;

        Member testUser = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .gender(gender)
                .dorm(dorm)
                .build();

        String json = objectMapper.writeValueAsString(testUser);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        JSONObject response = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(name, response.get("name"));
        assertEquals(email, response.get("email"));
        assertEquals(String.valueOf(gender), response.get("gender"));
        assertEquals(String.valueOf(dorm), response.get("dorm"));
    }

    @Test
    void singUpDuplicateUser() throws Exception{
        //given
        String name = "testUser";
        String email = "test@mail.com";
        String password = "testPw";
        Gender gender = Gender.MALE;
        Dorm dorm = Dorm.BONGSA;

        Member testUser = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .gender(gender)
                .dorm(dorm)
                .build();

        memberRepository.save(testUser);

        //when
        SignUpReq req = new SignUpReq();
        req.setName(name);
        req.setEmail(email);
        req.setPassword(password);
        req.setGender(gender);
        req.setDorm(dorm);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
                authService.signUp(req);
            });

    }
}
