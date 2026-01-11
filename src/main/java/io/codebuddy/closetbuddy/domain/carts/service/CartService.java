package io.codebuddy.closetbuddy.domain.carts.service;

import io.codebuddy.closetbuddy.domain.carts.Dto.CartAddRequestDto;
import io.codebuddy.closetbuddy.domain.carts.Dto.CartResponseDto;
import io.codebuddy.closetbuddy.domain.carts.entity.Cart;
import io.codebuddy.closetbuddy.domain.carts.entity.CartItem;
import io.codebuddy.closetbuddy.domain.carts.repository.CartItemRepository;
import io.codebuddy.closetbuddy.domain.carts.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Long createCart(CartAddRequestDto request, Long memberId) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(""));
    }


    @Transactional
    public List<CartResponseDto> getCartList(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId);

        if(cart == null) {
            return new ArrayList<>();
        }

        return  cart.getCartItems().stream()
                .map(CartResponseDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public void updateCart(CartAddRequestDto request, Long memberId, Integer cartStock) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);

        cartItem.updateStock(cartStock);
    }

    @Transactional
    public void deleteCartItem(Long memberId, Long cartId) {
        CartItem cartItem = cartItemRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 없는 상품입니다."));

        cartRepository.delete(cartItem);
    }
}
