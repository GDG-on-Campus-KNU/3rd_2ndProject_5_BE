package com.gdsc.dorm.auth.data.dto.req;

import com.gdsc.dorm.member.type.Dorm;
import com.gdsc.dorm.member.type.Gender;
import lombok.Data;

@Data
public class SignUpReq {
    private String name;
    private String email;
    private String password;
    private Gender gender;
    private Dorm dorm;
    private String birthYear;
    private String studentId;
}
