package io.codebuddy.closetbuddy.domain.common.repository;

import io.codebuddy.closetbuddy.domain.common.model.entity.RefreshTokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {

}
