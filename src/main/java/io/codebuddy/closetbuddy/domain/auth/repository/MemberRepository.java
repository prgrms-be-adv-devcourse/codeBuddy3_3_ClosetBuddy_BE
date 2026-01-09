package io.codebuddy.closetbuddy.domain.auth.repository;

import io.codebuddy.closetbuddy.domain.auth.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

}
