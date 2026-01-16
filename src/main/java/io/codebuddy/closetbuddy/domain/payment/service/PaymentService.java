package io.codebuddy.closetbuddy.domain.payment.service;

import io.codebuddy.closetbuddy.domain.payment.model.entity.Payment;
import io.codebuddy.closetbuddy.domain.payment.model.vo.PaymentRequest;
import io.codebuddy.closetbuddy.domain.payment.model.vo.PaymentResponse;

import java.util.List;

public interface PaymentService {

    public PaymentResponse payOrder(Long memberId, PaymentRequest request);

    public PaymentResponse payCancel(Long memberId, PaymentRequest request);

    PaymentResponse getPayment(Long memberId, Long orderId);

    List<PaymentResponse> getPayments(Long memberId);
}
