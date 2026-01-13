package io.codebuddy.closetbuddy.account.model.vo;

public record AccountResponse(
        Long memberId,
        Long balance,
        String message
) {
}
