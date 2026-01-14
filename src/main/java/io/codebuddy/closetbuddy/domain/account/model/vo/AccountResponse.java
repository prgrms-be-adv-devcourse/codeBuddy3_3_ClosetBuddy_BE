package io.codebuddy.closetbuddy.domain.account.model.vo;

public record AccountResponse(
        Long memberId,
        Long balance,
        String message
) {
}
