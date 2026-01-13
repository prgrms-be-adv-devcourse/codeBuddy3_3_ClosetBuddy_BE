package io.codebuddy.closetbuddy.domain.stores.model.dto;

public record UpsertStoreRequest(
        Long storeId,
        Long sellerId,
        String storeName
) {
}
