package io.codebuddy.closetbuddy.domain.orders.entity;

import io.codebuddy.closetbuddy.domain.orderItems.entity.OrderItem;
import io.codebuddy.closetbuddy.global.config.enumfile.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private List<OrderItem> orderItem = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    // 주문 생성
    public static Order createOrder(Member member, List<OrderItem> orderItems){
        Order order = new Order();

        order.setMember(member);
        order.orderStatus = OrderStatus.CREATED;
        order.createdAt = LocalDateTime.now();

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItems);
        }

        return order;

    }

    // 상태 변경 -> 주문 취소
    public void changeStatus(OrderStatus newStatus) {
        this.orderStatus = newStatus;
    }

    private void SetMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(List<OrderItem> orderItems) {
        this.orderItem.addAll(orderItems);
        orderItem.setOrder(this);
    }

    public Long getId() {
        return null;
    }
}
