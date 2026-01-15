package io.codebuddy.closetbuddy.domain.account.repository;

import io.codebuddy.closetbuddy.domain.account.model.entity.Account;
import io.codebuddy.closetbuddy.domain.account.model.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory,Long> {

    List<AccountHistory> OrderByCreatedAtDesc(Account account);

    Optional<AccountHistory> findByAccountAndAccountHistoryId(Account account, Long accountHistoryId);
}
