package io.codebuddy.closetbuddy.catalog.domain.stores.repository;

import io.codebuddy.closetbuddy.catalog.domain.stores.model.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreJpaRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreId(Long storeId);
}
