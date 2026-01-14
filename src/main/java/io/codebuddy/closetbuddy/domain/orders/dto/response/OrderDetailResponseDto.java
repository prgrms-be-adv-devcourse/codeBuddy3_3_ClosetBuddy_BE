package io.codebuddy.closetbuddy.domain.orders.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponseDto(
        Long orderId,
        String storeName,
        LocalDateTime orderDate,
        List<OrderItemDto> orderItems
) {
}
