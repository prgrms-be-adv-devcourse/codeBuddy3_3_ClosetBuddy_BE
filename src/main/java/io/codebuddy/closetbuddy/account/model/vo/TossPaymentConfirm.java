package io.codebuddy.closetbuddy.account.model.vo;

public record TossPaymentConfirm(
        String paymentKey,
        String orderId,
        Long amount
) {
}
