package io.codebuddy.closetbuddy.domain.oauth.repository;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

}
