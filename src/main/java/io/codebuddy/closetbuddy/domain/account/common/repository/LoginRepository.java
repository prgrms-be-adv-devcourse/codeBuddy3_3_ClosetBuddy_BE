package io.codebuddy.closetbuddy.domain.account.common.repository;

import io.codebuddy.closetbuddy.domain.account.common.model.entity.LoginMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<LoginMember, Long> {
    LoginMember findByUserid(String username);

}
