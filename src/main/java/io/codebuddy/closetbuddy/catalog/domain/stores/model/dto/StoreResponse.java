package io.codebuddy.closetbuddy.catalog.domain.stores.model.dto;

public record StoreResponse(
        Long storeId,
        Long sellerId,
        String storeName
) {
}
