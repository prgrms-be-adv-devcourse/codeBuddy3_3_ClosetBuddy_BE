package io.codebuddy.closetbuddy.domain.carts.controller;

import io.codebuddy.closetbuddy.domain.form.Login.security.auth.MemberPrincipalDetails;
import io.codebuddy.closetbuddy.domain.carts.dto.request.CartAddRequestDto;
import io.codebuddy.closetbuddy.domain.carts.dto.response.CartResponseDto;
import io.codebuddy.closetbuddy.domain.carts.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Long> createCart(
            @AuthenticationPrincipal MemberPrincipalDetails memberPrincipal,
            @Valid @RequestBody CartAddRequestDto request
    ) {
        Long cartItemId = cartService.createCart(
                memberPrincipal.getMember().getId(),
                request
        );
        return ResponseEntity.ok(cartItemId);
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDto>> getCart(
            @AuthenticationPrincipal MemberPrincipalDetails memberPrincipal
    ) {
        Long memberId = memberPrincipal.getMember().getId();
        List<CartResponseDto> cartList = cartService.getCartList(memberId);

        return ResponseEntity.ok(cartList);
    }

    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(
            @AuthenticationPrincipal MemberPrincipalDetails memberPrincipal,
            @PathVariable Long cartItemId,
            @RequestBody CartItemUpdateDto request
    ) {
        cartService.updateCartItemCount(
                memberPrincipal.getMember().getId(),
                cartItemId,
                request.count()
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal MemberPrincipalDetails memberPrincipal,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(
                memberPrincipal.getMember().getId(),
                cartItemId);
        return ResponseEntity.noContent().build();
    }

}
