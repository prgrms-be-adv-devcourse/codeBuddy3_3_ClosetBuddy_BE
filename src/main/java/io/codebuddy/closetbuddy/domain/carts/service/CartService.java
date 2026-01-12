package io.codebuddy.closetbuddy.domain.carts.service;

import io.codebuddy.closetbuddy.domain.carts.dto.request.CartAddRequestDto;
import io.codebuddy.closetbuddy.domain.carts.dto.response.CartResponseDto;
import io.codebuddy.closetbuddy.domain.carts.entity.Cart;
import io.codebuddy.closetbuddy.domain.carts.entity.CartItem;
import io.codebuddy.closetbuddy.domain.carts.repository.CartItemRepository;
import io.codebuddy.closetbuddy.domain.carts.repository.CartRepository;
import io.codebuddy.closetbuddy.domain.products.model.entity.Product;
import io.codebuddy.closetbuddy.domain.products.repository.ProductJpaRepository;
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
    private final MemberRepository memberRepository;
    private final ProductJpaRepository productJpaRepository;

    @Transactional
    public Long createCart(Long memberId, CartAddRequestDto request) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        Cart cart = cartRepository.findByMemberId(memberId);
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Product product = productJpaRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다."));

        CartItem cartItem = cartItemRepository.findByCartAndProductId(memberId);

        if(cartItem == null) {
            cartItem = cartItem.createCartItem
        }

        return cart.getId();
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
    public void updateCart(Long memberId, Long cartItemId, Integer cartCount) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                        .orElseThrow(()-> new IllegalArgumentException(""));

        if (!cartItem.getCart().getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        cartItem.updateCount(cartCount);
    }

    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 없는 상품입니다."));

        if (!cartItem.getCart().getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        cartItemRepository.delete(cartItem);
    }
}
