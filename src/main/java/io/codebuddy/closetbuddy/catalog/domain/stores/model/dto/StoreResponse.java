package io.codebuddy.closetbuddy.catalog.domain.stores.model.dto;

import io.codebuddy.closetbuddy.catalog.domain.stores.model.entity.Store;

public record StoreResponse(
        Long storeId,
        Long sellerId,
        String storeName
) {

    //Store 엔티티를 DTO로 변환해주는 메서드
    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getStoreId(),
                store.getSellerId(),
                store.getStoreName()
        );
    }
}
