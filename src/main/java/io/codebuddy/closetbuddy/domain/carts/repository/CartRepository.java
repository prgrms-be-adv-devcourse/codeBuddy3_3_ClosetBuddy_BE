package io.codebuddy.closetbuddy.domain.carts.repository;

import io.codebuddy.closetbuddy.domain.carts.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMemberId(Long memberId);
}
