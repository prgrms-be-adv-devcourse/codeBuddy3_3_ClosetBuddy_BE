package io.codebuddy.closetbuddy.catalog.domain.sellers.repository;

import io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SellerJpaRepository extends JpaRepository<Seller, Long> {

    Optional<Seller> findByMemberId(Long memberId);
}
