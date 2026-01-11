package io.codebuddy.closetbuddy.domain.orders.dto;

public record OrderItemDto(
        Long orderId,
        String storeName,
        String productName,
        Integer orderCount,
        Long orderPrice
) {
}
