package io.codebuddy.closetbuddy.domain.carts.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "cart_stock", nullable = false)
    private Integer cartStock;

    @Column(name = "cart_price", nullable = false)
    private Integer cartPrice;

    public void addCount(Integer count) {
        this.cartCount += count;
    }

    public void updateStock(Integer cartStock) {
        this.cartStock += count;
    }

}
