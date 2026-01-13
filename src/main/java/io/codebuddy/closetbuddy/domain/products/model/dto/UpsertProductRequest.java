package io.codebuddy.closetbuddy.domain.products.model.dto;

public record UpsertProductRequest(
        Long productId,
        String productName,
        Long productPrice,
        int productStock,
        Long storeId,
        String imageUrl,
        Category category
) {
}
