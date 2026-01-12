package io.codebuddy.closetbuddy.domain.carts.dto.request;

public record CartAddRequestDto(
        Long productId,
        Integer cartStock
){
}
