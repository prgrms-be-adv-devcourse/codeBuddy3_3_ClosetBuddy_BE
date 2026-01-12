package io.codebuddy.closetbuddy.domain.order.controller;

//import io.codebuddy.closetbuddy.domain.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

//    private final OrderService orderService;
//
//    @PostMapping
//    public ResponseEntity<OrderRequestDto> createOrder(
//            @RequestBody OrderRequestDto request
//    ){
//        return ResponseEntity.ok(request);
//    }
//
//    @GetMapping("/orderList")
//    public ResponseEntity<List<OrderResponseDto>> getOrder(
//            @AuthenticationPrincipal UserPrincipal userPrincipal
//    ){
//        Long memberId = userPrincipal.getMember().getId();
//        List<OrderResponseDto> orderResponseDto = orderService.getOrder(memberId);
//
//        return ResponseEntity.ok(orderResponseDto);
//    }
//
//    @GetMapping("/{orderId}")
//    public ResponseEntity<OrderResponseDto> getDetailOrder(
//            @PathVariable Long orderId
//    ){
//        OrderResponseDto response = orderService.getDetailOrder(orderId);
//        return ResponseEntity.ok(response);
//    }
//
//    @PatchMapping("/{orderId}/status")
//    public ResponseEntity<Void> canceledOrder(
//            @PathVariable Long orderId
//    ){
//        orderService.cancelledOrder(orderId);
//        return ResponseEntity.ok().build();
//    }
}
