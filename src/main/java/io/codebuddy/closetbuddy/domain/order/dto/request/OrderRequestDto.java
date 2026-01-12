package io.codebuddy.closetbuddy.domain.order.dto.request;

public record OrderRequestDto(
        Long productName,
        Integer orderCount,
        Long orderPrice
) {

}
