package io.codebuddy.closetbuddy.domain.orders.entity;

import io.codebuddy.closetbuddy.domain.orderItems.entity.OrderItem;
import io.codebuddy.closetbuddy.domain.orders.OrderStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "order_count")
    private Long orderAmount;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item")
    private OrderItem orderItem;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeStatus(OrderStatus newStatus) {
        this.orderStatus = newStatus;
    }

}
