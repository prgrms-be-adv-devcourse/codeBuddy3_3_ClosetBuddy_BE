//package io.codebuddy.closetbuddy.domain.orders.service;
//
//import io.codebuddy.closetbuddy.domain.oauth.repository.MemberRepository;
//import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
//import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderDetailResponseDto;
//import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderItemDto;
//import io.codebuddy.closetbuddy.domain.orders.entity.OrderItem;
//import io.codebuddy.closetbuddy.domain.products.model.entity.Product;
//import io.codebuddy.closetbuddy.domain.products.repository.ProductJpaRepository;
//import io.codebuddy.closetbuddy.global.config.enumfile.OrderStatus;
//import io.codebuddy.closetbuddy.domain.orders.dto.request.OrderRequestDto;
//import io.codebuddy.closetbuddy.domain.orders.entity.Order;
//import io.codebuddy.closetbuddy.domain.orders.repository.OrderRepository;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//
//    private final MemberRepository memberRepository;
//    private final ProductJpaRepository productJpaRepository;
//    private final OrderRepository orderRepository;
//
//    @Transactional
//    public Long createOrder(Long memberId, OrderRequestDto requestDto) {
//
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
//
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (OrderItemDto itemDto : requestDto.orderItemDtoList()) {
//            Product product = productJpaRepository.findById(itemDto.productId())
//                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
//
//            OrderItem orderItem = OrderItem.createOrderItem(product, product.getProductPrice(), itemDto.orderCount());
//
//            orderItems.add(orderItem);
//        }
//
//        Order order = Order.createOrder(member, orderItems);
//        orderRepository.save(order);
//
//        return order.getOrderId();
//
//    }
//
//    @Transactional(readOnly = true)
//    public Order getOrder(Long memberId) {
//        return orderRepository.findById(memberId)
//                .orElseThrow(() -> new EntityNotFoundException("주문 내역이 존재하지 않습니다."));
//    }
//
//
//
//    @Transactional(readOnly = true)
//    public OrderDetailResponseDto getDetailOrder(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("주문이 없습니다."));
//
//        List<OrderItemDto> itemDto = order.getOrderItem().stream()
//                .map(orderItem -> new OrderItemDto(
//
//
//                )).toList();
//
//
//        return new OrderDetailResponseDto(
//
//        );
//
//    }
//
//    @Transactional
//    public void cancelledOrder(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("주문한 내역이 아직 없습니다."));
//
//        order.changeStatus(OrderStatus.CANCELED);
//    }
//}
