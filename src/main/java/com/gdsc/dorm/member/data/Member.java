package com.gdsc.dorm.member.data;

import com.gdsc.dorm.member.type.Dorm;
import com.gdsc.dorm.member.type.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private Gender gender;

    private Dorm dorm;
}
