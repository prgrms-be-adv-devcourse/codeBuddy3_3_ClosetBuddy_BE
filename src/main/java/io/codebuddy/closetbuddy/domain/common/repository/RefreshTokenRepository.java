package io.codebuddy.closetbuddy.domain.common.repository;
import io.codebuddy.closetbuddy.domain.common.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
