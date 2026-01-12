package io.codebuddy.closetbuddy.domain.order.dto.response;

public record OrderDetailResponse(
        String storeName,
        Long productPrice,
        String orderCount,
        String orderPrice
) {

}
