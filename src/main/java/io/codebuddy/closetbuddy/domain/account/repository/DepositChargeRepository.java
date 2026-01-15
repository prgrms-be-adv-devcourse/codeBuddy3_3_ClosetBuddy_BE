package io.codebuddy.closetbuddy.domain.account.repository;

import io.codebuddy.closetbuddy.domain.account.model.entity.DepositCharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositChargeRepository extends JpaRepository<DepositCharge,Long> {
}
