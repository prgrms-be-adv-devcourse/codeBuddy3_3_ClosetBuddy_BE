package io.codebuddy.closetbuddy.domain.orderItems.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "order_count")
    private Integer orderCount;

    @Column(name = "order_price")
    private BigDecimal orderPrice;
}
