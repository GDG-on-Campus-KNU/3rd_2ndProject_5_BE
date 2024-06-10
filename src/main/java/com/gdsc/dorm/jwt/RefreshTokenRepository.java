package com.gdsc.dorm.jwt;

import com.gdsc.dorm.jwt.data.RefreshToken;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    boolean existsByMemberId(Long memberId);
    @Transactional
    void deleteByMemberId(Long memberId);
}
