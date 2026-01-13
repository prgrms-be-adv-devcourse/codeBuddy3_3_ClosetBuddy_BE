package io.codebuddy.closetbuddy.domain.sellers.model.dto;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;

public record UpsertSellerRequest(
        Long sellerId,
        Member member,
        String sellerName
) {
}
