package io.codebuddy.closetbuddy.domain.carts.Dto.response;

import io.codebuddy.closetbuddy.domain.carts.entity.CartItem;

public record CartResponseDto(
        String productName,
        Integer cartCount

){
    public CartResponseDto(CartItem entity){
        this(
                entity.getProduct().getProductName(),
                entity.getCartStock()
        );
    }

}
