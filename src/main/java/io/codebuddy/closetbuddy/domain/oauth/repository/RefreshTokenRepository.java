package io.codebuddy.closetbuddy.domain.oauth.repository;
import io.codebuddy.closetbuddy.domain.oauth.Entity.RefreshToken ;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
