package io.codebuddy.closetbuddy.catalog.domain.sellers.repository;

import io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SellerJpaRepository extends JpaRepository<Seller, Long> {

}
