package io.codebuddy.closetbuddy.catalog.domain.products.model.dto;

public record ProductResponse(
        Long productId,
        String productName,
        Long productPrice,
        int productStock,
        Long storeId
        //추후 storeName 포함 가능
) {
}
