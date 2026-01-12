package io.codebuddy.closetbuddy.domain.carts.controller;

import io.codebuddy.closetbuddy.domain.carts.dto.request.CartAddRequestDto;
import io.codebuddy.closetbuddy.domain.carts.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Long> createCart(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CartAddRequestDto request
            )
    {
        Long cartItemId = cartService.createCart(
                userPrincipal.getMember().getId(),
                request
        );
        return ResponseEntity.ok(cartItemId);
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getCart(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    )
    {
        Long memberId = userPrincipal.getMember().getId();
        List<CartResponseDto> cartList = cartService.getCartList(memberId);

        return ResponseEntity.ok(cartList);
    }

    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long cartItemId,
            @RequestBody CartItemUpdateDto request
    )
    {
        cartService.updateCartItemCount(
                userPrincipal.getMember().getId(),
                cartItemId,
                request.count()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long cartItemId
    )
    {
        cartService.deleteCartItem(
                userPrincipal.getMember().getId(),
                cartItemId);
        return ResponseEntity.noContent().build();
    }

}
