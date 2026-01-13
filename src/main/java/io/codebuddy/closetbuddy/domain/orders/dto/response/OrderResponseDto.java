package io.codebuddy.closetbuddy.domain.orders.dto.response;

public record OrderResponseDto(
        String orderId,
        String productName,
        Long orderPrice

) {

}
