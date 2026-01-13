package io.codebuddy.closetbuddy.domain.oauth.repository;

import io.codebuddy.closetbuddy.domain.oauth.Entity.RefreshTokenBlackList ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {
}
