package io.codebuddy.closetbuddy.catalog.domain.products.model.dto;

public record UpsertProductRequest(
        Long productId,
        String productName,
        Long productPrice,
        int productStock,
        Long storeId
) {
}
