package io.codebuddy.closetbuddy.domain.orders.controller;

import io.codebuddy.closetbuddy.domain.orders.dto.request.OrderRequestDto;
import io.codebuddy.closetbuddy.domain.orders.dto.response.OrderResponseDto;
import io.codebuddy.closetbuddy.domain.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping("/orderList")
//    public ResponseEntity<List<OrderResponseDto>> getOrder(
//            @AuthenticationPrincipal CustomUserDetails userDetails
//    ){
//        Long memberId = userDetails.getMember().getId();
//        List<OrderResponseDto> orderResponseDto = orderService.getOrder(memberId);
//
//        return ResponseEntity.ok(orderResponseDto);
//    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getDetailOrder(
            @PathVariable Long orderId
    ){
        OrderResponseDto response = orderService.getDetailOrder(orderId);
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
