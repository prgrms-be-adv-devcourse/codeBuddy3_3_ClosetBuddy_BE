package io.codebuddy.closetbuddy.catalog.domain.stores.model.dto;

import java.util.List;

public record StoreListResponse(
        List<StoreResponse> stores
) {
}
