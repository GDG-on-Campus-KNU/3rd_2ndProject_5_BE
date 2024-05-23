package com.gdsc.dorm.auth;

import com.gdsc.dorm.auth.data.dto.req.LoginReq;
import com.gdsc.dorm.auth.data.dto.req.SignUpReq;
import com.gdsc.dorm.auth.data.dto.res.LoginRes;
import com.gdsc.dorm.member.data.dto.res.MemberGetRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberGetRes> signup(@RequestBody SignUpReq req){
        return authService.signUp(req);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq req) {
        return authService.login(req);
    }
}
