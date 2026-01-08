package io.codebuddy.closetbuddy.catalog.domain.products.repository;

import io.codebuddy.closetbuddy.catalog.domain.products.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductId(Long productId);
}
