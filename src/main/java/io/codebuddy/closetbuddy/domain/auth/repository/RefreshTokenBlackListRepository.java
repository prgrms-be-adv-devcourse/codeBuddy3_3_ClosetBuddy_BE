package io.codebuddy.closetbuddy.domain.auth.repository;

import io.codebuddy.closetbuddy.domain.auth.Entity.RefreshTokenBlackList ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {
}
