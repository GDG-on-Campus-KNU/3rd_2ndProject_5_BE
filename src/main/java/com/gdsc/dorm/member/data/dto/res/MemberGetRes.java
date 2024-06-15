package com.gdsc.dorm.member.data.dto.res;

import com.gdsc.dorm.member.data.Member;
import com.gdsc.dorm.member.type.Dorm;
import com.gdsc.dorm.member.type.Gender;
import lombok.Data;

@Data
public class MemberGetRes {
    private String name;
    private String email;
    private Gender gender;
    private Dorm dorm;

    public MemberGetRes(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.dorm = member.getDorm();
    }
}
