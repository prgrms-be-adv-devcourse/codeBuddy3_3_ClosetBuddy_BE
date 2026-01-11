package io.codebuddy.closetbuddy.domain.carts.Dto;

import jakarta.validation.constraints.Min;

public record CartItemUpdateDto(
        @Min(value = 1, message = "최소 1개여야 합니다.")
        Integer cartStock
){
}
