package io.codebuddy.closetbuddy.domain.orders.dto;

public record OrderRequestDto(
        Long productName,
        Integer orderCount,
        Long orderPrice
) {

}
