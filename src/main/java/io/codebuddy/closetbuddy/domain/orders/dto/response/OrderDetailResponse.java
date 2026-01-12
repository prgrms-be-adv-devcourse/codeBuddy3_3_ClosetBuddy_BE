package io.codebuddy.closetbuddy.domain.orders.dto.response;

public record OrderDetailResponse(
        String storeName,
        Long productPrice,
        String orderCount,
        String orderPrice
) {

}
