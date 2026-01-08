package io.codebuddy.closetbuddy.catalog.domain.sellers.model.dto;

public record UpsertSellerRequest(
        Long sellerId,
        Long memberId
) {
}
