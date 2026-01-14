package io.codebuddy.closetbuddy.domain.orders.entity;

import io.codebuddy.closetbuddy.domain.products.model.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "order_count")
    private Integer orderCount;

    @Column(name = "order_price")
    private BigDecimal orderPrice;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private String productName; // 상품 이름 가져오기
    private Long productPrice; // 상품 가격 가져오기
    private Long storeName; // 가게 이름 가져오기

    public static OrderItem createOrderItem(Product product, Long productPrice, Integer integer) {


    }


    protected void setOrder(Order order) {
        this.order = order;
    }

}
