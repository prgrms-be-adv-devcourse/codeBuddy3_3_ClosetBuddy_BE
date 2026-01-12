package io.codebuddy.closetbuddy.account.repository;

import io.codebuddy.closetbuddy.account.model.entity.Account;
import io.codebuddy.closetbuddy.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByMember(Member foundMember);
}
