package io.codebuddy.closetbuddy.domain.sellers.model.dto;

public record UpsertSellerRequest(
        Long sellerId,
        Long memberId,
        String sellerName
) {
}
