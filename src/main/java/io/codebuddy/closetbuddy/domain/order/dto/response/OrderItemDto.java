package io.codebuddy.closetbuddy.domain.order.dto.response;

public record OrderItemDto(
        Long orderId,
        String storeName,
        String productName,
        Integer orderCount,
        Long orderPrice
) {
}
