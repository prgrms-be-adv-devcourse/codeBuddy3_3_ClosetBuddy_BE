package io.codebuddy.closetbuddy.domain.orders.dto;

public record OrderResponseDto(
        String orderId,
        String productName,
        Long orderPrice

) {

}
