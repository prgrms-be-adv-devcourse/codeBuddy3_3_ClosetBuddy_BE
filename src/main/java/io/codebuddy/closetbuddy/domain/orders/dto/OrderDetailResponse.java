package io.codebuddy.closetbuddy.domain.orders.dto;

public record OrderDetailResponse(
        String storeName,
        Long productPrice,
        String orderCount,
        String orderPrice
) {

}
