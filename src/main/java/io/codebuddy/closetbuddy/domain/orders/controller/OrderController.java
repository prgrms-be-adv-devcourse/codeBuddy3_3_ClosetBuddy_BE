package io.codebuddy.closetbuddy.domain.orders.controller;

import io.codebuddy.closetbuddy.domain.orders.dto.OrderRequestDto;
import io.codebuddy.closetbuddy.domain.orders.dto.OrderResponseDto;
import io.codebuddy.closetbuddy.domain.orders.dto.OrderStatusRequestDto;
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

    @Operation(
            summary = "주문 내역 생성",
            description = "주문 내역을 생성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "주문 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping
    public ResponseEntity<OrderRequestDto> createOrder(
            @RequestBody OrderRequestDto request
    ){
        return ResponseEntity.ok(request);
    }

    @Operation(
            summary = "회원 전체 주문 조회",
            description = "사용자가 전체 주문을 조회할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/orderList")
    public ResponseEntity<List<OrderResponseDto>> getOrder(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long memberId = userDetails.getMember().getId();
        List<OrderResponseDto> orderResponseDto = orderService.getOrder(memberId);

        return ResponseEntity.ok(orderResponseDto);
    }

    @Operation(
            summary = "주문 상세 조회",
            description = "주문을 상세 조회 할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 상세 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/orderDetail")
    public void getDetailOrder(
            @RequestParam Long orderId
    ){
        orderService.searchDetailOrder(orderId);
    }


    @Operation(
            summary = "주문 삭제 - 상태만 변경합니다.",
            description = "주문을 삭제할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Void> canceledOrder(
            @PathVariable Long orderId,
            @RequestBody OrderStatusRequestDto request
    ){
        orderService.cancelledOrder(orderId, request.orderStatus());
        return ResponseEntity.ok().build();
    }
}
