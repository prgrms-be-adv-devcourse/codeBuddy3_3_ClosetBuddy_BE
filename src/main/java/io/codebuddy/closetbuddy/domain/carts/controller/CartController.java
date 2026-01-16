package io.codebuddy.closetbuddy.domain.carts.controller;

import io.codebuddy.closetbuddy.domain.carts.dto.request.CartCreateRequestDto;
import io.codebuddy.closetbuddy.domain.carts.dto.response.CartGetResponseDto;
import io.codebuddy.closetbuddy.domain.carts.service.CartService;
import io.codebuddy.closetbuddy.domain.form.Login.security.auth.MemberDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    /**
     * 장바구니를 생성합니다.
     * memberId를 받지 않으면 남의 장바구니에 물건을 담아버리거나
     * 남의 장바구니를 엿볼 수 있으므로 memberId를 받는다.
     * @param memberPrincipalDetails
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<Long> createCart(
            @AuthenticationPrincipal MemberDetails memberPrincipalDetails,
            @Valid @RequestBody CartCreateRequestDto request
    ) {
        Long cartItemId = cartService.createCart(memberPrincipalDetails.getMember().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemId);
    }

    /**
     * 장바구니를 조회합니다.
     * 회원 아이디를 통해 조회합니다.
     * @param memberPrincipalDetails
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CartGetResponseDto>> getCart(
            @AuthenticationPrincipal MemberDetails memberPrincipalDetails
    ) {
        Long memberId = memberPrincipalDetails.getMember().getId();
        List<CartGetResponseDto> cartList = cartService.getCartList(memberId);

        return ResponseEntity.ok(cartList);
    }


    /**
     * memberId를 통해 장바구니 수량을 수정합니다.
     * 수량이 1개 미만으로 떨어지면 예외를 발생시킵니다.
     * @param memberPrincipal
     * @param cartItemId
     * @param cartCount
     * @return
     */
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(
            @AuthenticationPrincipal MemberDetails memberPrincipal,
            @PathVariable Long cartItemId,
            @RequestParam Integer cartCount
    ) {
        if(cartCount == null || cartCount < 1) {
            throw new IllegalArgumentException("수량은 최소 1개 이상이어야 합니다.");
        }

        Long memberId = memberPrincipal.getMember().getId();

        cartService.updateCart(memberId, cartItemId, cartCount);

        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @AuthenticationPrincipal MemberDetails memberPrincipal,
            @PathVariable Long cartItemId
    ) {
        Long memberId = memberPrincipal.getMember().getId();

        cartService.deleteCartItem(memberPrincipal.getMember().getId(), cartItemId);

        return ResponseEntity.noContent().build();
    }

}
