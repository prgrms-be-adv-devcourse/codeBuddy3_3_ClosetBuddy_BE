package io.codebuddy.closetbuddy.domain.orders.service;

import io.codebuddy.closetbuddy.domain.orders.OrderStatus;
import io.codebuddy.closetbuddy.domain.orders.dto.OrderCreateRequestDto;
import io.codebuddy.closetbuddy.domain.orders.entity.Order;
import io.codebuddy.closetbuddy.domain.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void CreateOrder(OrderCreateRequestDto request){
        Order order = new Order();

        orderRepository.save(order);
    }

    @Transactional
    public void searchOrder(Long memberId) {
        orderRepository.findAll();
    }

    @Transactional
    public void searchDetailOrder(Long orderId){
        orderRepository.findAllById(Collections.singleton(orderId));
    }

    @Transactional
    public void cancelledOrder(Long orderId, OrderStatus request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문하신 내역이 아직 없습니다."));

        order.changeStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
