package io.codebuddy.closetbuddy.domain.stores.repository;

import io.codebuddy.closetbuddy.domain.stores.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface StoreJpaRepository extends JpaRepository<Store, Long> {

    List<Store> findBySellerId(Long sellerId);
}
