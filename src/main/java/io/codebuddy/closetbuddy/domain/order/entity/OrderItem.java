package io.codebuddy.closetbuddy.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
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

//    @ManyToOne
//    @JoinColumn(name = "orders_id")
//    private Order order;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private Product product;

    private String productName; // 상품 이름 가져오기
    private Long productPrice; // 상품 가격 가져오기

}
