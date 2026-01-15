package io.codebuddy.closetbuddy.domain.payment.controller;

import io.codebuddy.closetbuddy.domain.form.Login.security.auth.MemberPrincipalDetails;
import io.codebuddy.closetbuddy.domain.payment.model.vo.PaymentRequest;
import io.codebuddy.closetbuddy.domain.payment.model.vo.PaymentResponse;
import io.codebuddy.closetbuddy.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 수행
    @PostMapping
    public ResponseEntity<PaymentResponse> payOrder(
            @AuthenticationPrincipal MemberPrincipalDetails principal,
            @RequestBody PaymentRequest request
    ) {
        Long memberId = principal.getMember().getId();
        return ResponseEntity.ok(paymentService.payOrder(memberId, request));
    }

    // 결제 취소
    @PostMapping("/cancel")
    public ResponseEntity<PaymentResponse> cancelPayment(
            @AuthenticationPrincipal MemberPrincipalDetails principal,
            @RequestBody PaymentRequest request
    ) {
        Long memberId = principal.getMember().getId();
        return ResponseEntity.ok(paymentService.payCancel(memberId, request));
    }

    // 결제 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentDetail(
            @AuthenticationPrincipal MemberPrincipalDetails principal,
            @PathVariable Long orderId
    ) {
        Long memberId = principal.getMember().getId();
        return ResponseEntity.ok(paymentService.getPayment(memberId, orderId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPaymentList(
            @AuthenticationPrincipal MemberPrincipalDetails principal
    ) {
        return ResponseEntity.ok(paymentService.getPayments(principal.getMember().getId()));
    }

}
