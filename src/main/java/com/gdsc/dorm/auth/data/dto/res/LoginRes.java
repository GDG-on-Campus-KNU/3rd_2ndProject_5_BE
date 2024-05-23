package com.gdsc.dorm.auth.data.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRes {
    private String accessToken;
    private String refreshToken;
}
