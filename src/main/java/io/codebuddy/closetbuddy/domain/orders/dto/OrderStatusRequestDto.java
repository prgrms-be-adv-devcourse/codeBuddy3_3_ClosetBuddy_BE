package io.codebuddy.closetbuddy.domain.orders.dto;


import io.codebuddy.closetbuddy.global.config.enumfile.OrderStatus;

public record OrderStatusRequestDto(
        OrderStatus orderStatus
) {
}
