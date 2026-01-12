package io.codebuddy.closetbuddy.domain.order.dto.response;

public record OrderResponseDto(
        String orderId,
        String productName,
        Long orderPrice

) {

}
