package io.codebuddy.closetbuddy.domain.orders.dto.request;

import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderItemDto;

import java.util.List;

public record OrderRequestDto(
        Long productName,
        Integer orderCount,
        Long orderPrice,
        List<OrderItemDto> orderItemDtoList
) {

}
