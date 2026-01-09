package io.codebuddy.closetbuddy.domain.auth.repository;
import io.codebuddy.closetbuddy.domain.auth.Entity.RefreshToken ;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
