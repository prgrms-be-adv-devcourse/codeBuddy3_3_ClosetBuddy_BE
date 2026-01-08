package io.codebuddy.closetbuddy.catalog.domain.products.service;

import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.UpsertProductRequest;
import io.codebuddy.closetbuddy.catalog.domain.products.model.entity.Product;
import io.codebuddy.closetbuddy.catalog.domain.products.repository.ProductJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;

    @Transactional
    public Product save(UpsertProductRequest request) {

        Product product = Product.builder()
                .productId(request.productId())
                .productName(request.ProductName())
                .productPrice(request.productPrice())
                .productStock(request.productStock())
                .storeId(request.storeID())
                .build();

        return productJpaRepository.save(product);
    }
}
