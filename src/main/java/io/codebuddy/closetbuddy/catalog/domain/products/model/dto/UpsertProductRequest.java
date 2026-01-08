package io.codebuddy.closetbuddy.catalog.domain.products.model.dto;

public record UpsertProductRequest(
        Long productId,
        String ProductName,
        Long productPrice,
        int productStock,
        Long storeID
) {
}
