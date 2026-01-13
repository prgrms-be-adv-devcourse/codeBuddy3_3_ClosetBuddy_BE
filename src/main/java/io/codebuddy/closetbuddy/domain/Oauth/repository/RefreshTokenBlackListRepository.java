package io.codebuddy.closetbuddy.domain.Oauth.repository;

import io.codebuddy.closetbuddy.domain.Oauth.Entity.RefreshTokenBlackList ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {
}
