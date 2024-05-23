package com.gdsc.dorm.auth.data.dto.req;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
