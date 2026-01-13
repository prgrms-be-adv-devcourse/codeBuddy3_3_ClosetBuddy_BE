package io.codebuddy.closetbuddy.domain.carts.entity;

import io.codebuddy.closetbuddy.domain.products.model.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "cart_count", nullable = false)
    private Integer cartCount;

    @Column(name = "cart_price", nullable = false)
    private Long cartPrice;


    public void updateCount(Integer cartCount) {
        this.cartCount += cartCount;
    }

}
