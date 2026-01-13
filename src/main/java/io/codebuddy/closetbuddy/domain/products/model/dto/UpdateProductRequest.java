package io.codebuddy.closetbuddy.domain.products.model.dto;

public record UpdateProductRequest(
        String productName,
        Long productPrice,
        int productStock,
        Long storeId,
        String imageUrl,
        Category category
) {
}
