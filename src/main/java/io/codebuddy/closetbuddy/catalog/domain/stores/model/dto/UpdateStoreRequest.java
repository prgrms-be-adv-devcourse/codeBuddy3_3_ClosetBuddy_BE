package io.codebuddy.closetbuddy.catalog.domain.stores.model.dto;

public record UpdateStoreRequest(
        String storeName
        //추후 가게 설명, 이미지 등 추가 가능
) {
}
