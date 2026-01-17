package io.codebuddy.closetbuddy.domain.settlement.repository;

import io.codebuddy.closetbuddy.domain.settlement.model.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementJpaRepository extends JpaRepository<Settlement, Long> {
}
