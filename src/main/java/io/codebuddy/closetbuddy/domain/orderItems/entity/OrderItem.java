package io.codebuddy.closetbuddy.domain.orderItems.entity;

import io.codebuddy.closetbuddy.domain.orders.entity.Order;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Getter
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "order_count")
    private Integer orderCount;

    @Column(name = "order_price")
    private BigDecimal orderPrice;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order order;

    private Long orderId; // 주문 번호

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private String productName; // 상품 이름 가져오기
    private Long producPrice; // 상품 가격 가져오기


    private createOrderItem(){

    }

}
