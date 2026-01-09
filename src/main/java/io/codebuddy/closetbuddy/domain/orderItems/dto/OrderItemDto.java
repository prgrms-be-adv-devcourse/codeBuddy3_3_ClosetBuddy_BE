package io.codebuddy.closetbuddy.domain.orderItems.dto;

public record OrderItemDto(
        Long orderId,
        String storeName,
        String productName,
        Integer orderCount,
        Long orderPrice
) {
}
