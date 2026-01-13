package io.codebuddy.closetbuddy.domain.orders.service;

import io.codebuddy.closetbuddy.domain.oauth.repository.MemberRepository;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderItemDto;
import io.codebuddy.closetbuddy.domain.orders.entity.OrderItem;
import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderResponseDto;
import io.codebuddy.closetbuddy.domain.products.model.entity.Product;
import io.codebuddy.closetbuddy.domain.products.repository.ProductJpaRepository;
import io.codebuddy.closetbuddy.global.config.enumfile.OrderStatus;
import io.codebuddy.closetbuddy.domain.orders.dto.request.OrderRequestDto;
import io.codebuddy.closetbuddy.domain.orders.entity.Order;
import io.codebuddy.closetbuddy.domain.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ProductJpaRepository productJpaRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long createOrder(OrderRequestDto request){

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemDto itemDto : request.getItems()){
            Product product = productJpaRepository.findById(itemDto.getProductId())
                    .orElseThrow(()->new IllegalArgumentException("회원이 존재하지 않습니다."));

            OrderItem orderItem = OrderItem.createOrderItem(product, product.getPrice(), itemDto.getCount());
            orderItems.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItems);
        orderRepository.save(order);

        return order.getOrderId();
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrder(Long memberId) {
        List<Order> orders = orderRepository.findAllByMemberId(memberId);

        return orders.stream()

                .map(order -> {
                    OrderResponseDto orderResponseDto = new OrderResponseDto();
                    orderResponseDto.setOrderId(order.getOrderId());
                    return orderResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getDetailOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 없습니다."));

        return new OrderResponseDto(order);
    }

    @Transactional
    public void cancelledOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문한 내역이 아직 없습니다."));

        order.changeStatus(OrderStatus.CANCELED);
    }
}
