package io.codebuddy.closetbuddy.domain.orders.dto.response;

import io.codebuddy.closetbuddy.domain.orders.entity.Order;

import java.util.List;

public record OrderResponseDto(
        Long orderId,
        List<String> productName,
        Long orderPrice

) {


}
