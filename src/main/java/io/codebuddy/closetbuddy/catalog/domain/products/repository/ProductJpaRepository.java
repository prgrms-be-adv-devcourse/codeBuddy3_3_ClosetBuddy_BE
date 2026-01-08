package io.codebuddy.closetbuddy.catalog.domain.products.repository;

import io.codebuddy.closetbuddy.catalog.domain.products.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductId(Long productId);
}
