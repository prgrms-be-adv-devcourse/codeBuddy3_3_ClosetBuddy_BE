package io.codebuddy.closetbuddy.catalog.domain.stores.model.dto;

public record UpsertStoreRequest(
        Long storeId,
        Long sellerId,
        String storeName
) {
}
