package io.codebuddy.closetbuddy.domain.orderItems.dto;

public record OrderDetailResponse(
        String storeName,
        Long productPrice,
        String orderCount,
        String orderPrice
) {

}
