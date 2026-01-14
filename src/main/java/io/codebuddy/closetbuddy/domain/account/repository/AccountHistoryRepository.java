package io.codebuddy.closetbuddy.domain.account.repository;

import io.codebuddy.closetbuddy.domain.account.model.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory,Long> {
}
