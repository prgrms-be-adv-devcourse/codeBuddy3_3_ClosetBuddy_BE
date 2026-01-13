package io.codebuddy.closetbuddy.domain.orders.dto.request;

public record OrderRequestDto(
        Long productName,
        Integer orderCount,
        Long orderPrice
) {

}
