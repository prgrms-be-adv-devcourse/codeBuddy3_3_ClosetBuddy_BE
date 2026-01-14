package io.codebuddy.closetbuddy.domain.account.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountResponse(
        @NotNull
        Long memberId,

        @NotNull
        Long balance,

        String message
) {
}
