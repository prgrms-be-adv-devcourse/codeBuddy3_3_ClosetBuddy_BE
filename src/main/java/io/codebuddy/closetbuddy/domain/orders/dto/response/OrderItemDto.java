package io.codebuddy.closetbuddy.domain.orders.dto.response;


import java.math.BigDecimal;

public record OrderItemDto(
        Long productId,
        String storeName,
        String productName,
        Integer orderCount,
        BigDecimal orderPrice
) {
}
