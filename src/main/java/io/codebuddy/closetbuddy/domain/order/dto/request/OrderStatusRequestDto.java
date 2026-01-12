package io.codebuddy.closetbuddy.domain.order.dto.request;


import io.codebuddy.closetbuddy.global.config.enumfile.OrderStatus;

public record OrderStatusRequestDto(
        OrderStatus orderStatus
) {

}
