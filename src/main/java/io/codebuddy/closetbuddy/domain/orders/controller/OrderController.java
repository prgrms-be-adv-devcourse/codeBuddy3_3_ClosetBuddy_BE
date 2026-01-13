package io.codebuddy.closetbuddy.domain.orders.controller;

import io.codebuddy.closetbuddy.domain.form.Login.security.auth.MemberPrincipalDetails;
import io.codebuddy.closetbuddy.domain.orders.dto.request.OrderRequestDto;
import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderDetailResponseDto;
import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderResponseDto;
import io.codebuddy.closetbuddy.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderRequestDto> createOrder(
            @RequestBody OrderRequestDto request
    ){
        return ResponseEntity.ok(request);
    }

    @GetMapping("/orderList")
    public ResponseEntity<List<OrderResponseDto>> getOrder(
            @AuthenticationPrincipal MemberPrincipalDetails memberPrincipal
    ){
        Long memberId = memberPrincipal.getMember().getId();
        List<OrderResponseDto> orderResponseDto = orderService.getOrder(memberId);

        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailResponseDto> getDetailOrder(
            @PathVariable Long orderId
    ){
        OrderDetailResponseDto response = orderService.getDetailOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> canceledOrder(
            @PathVariable Long orderId
    ){
        orderService.cancelledOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
