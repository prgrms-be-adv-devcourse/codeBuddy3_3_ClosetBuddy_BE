package io.codebuddy.closetbuddy.domain.account.repository;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Member, Long> {
    Member findByUserid(String username);

}
