package io.codebuddy.closetbuddy.domain.Oauth.repository;
import io.codebuddy.closetbuddy.domain.Oauth.Entity.RefreshToken ;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
