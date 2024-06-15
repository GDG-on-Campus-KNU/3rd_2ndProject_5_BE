package com.gdsc.dorm.member.data;

import com.gdsc.dorm.checklist.data.MateChecklist;
import com.gdsc.dorm.checklist.data.UserChecklist;
import com.gdsc.dorm.member.type.Dorm;
import com.gdsc.dorm.member.type.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@Entity
@Getter
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private Gender gender;

    private Dorm dorm;

    private String birthYear;

    @Column(unique = true)
    private String studentId;

    @OneToOne
    @JoinColumn(name = "userChecklist_id")
    private UserChecklist userChecklist;

    @OneToOne
    @JoinColumn(name = "mateChecklist_id")
    private MateChecklist mateChecklist;

    @Builder
    public Member(String name, String email, String password, Gender gender, Dorm dorm, String birthYear, String studentId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dorm = dorm;
        this.birthYear = birthYear;
        this.studentId = studentId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void updateMemberChecklist(UserChecklist userChecklist) {
        this.userChecklist = userChecklist;
    }

    public void updateMateChecklist(MateChecklist mateChecklist) {
        this.mateChecklist = mateChecklist;
    }
}
