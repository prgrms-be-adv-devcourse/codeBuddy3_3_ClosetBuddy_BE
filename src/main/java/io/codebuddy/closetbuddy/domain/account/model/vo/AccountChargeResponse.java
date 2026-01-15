package io.codebuddy.closetbuddy.domain.account.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AccountChargeResponse(
        @NotNull
        Long accountAmount, //예치된 금액

        @NotNull
        @Positive
        Long balance, //총 예치 금액

        @NotBlank
        LocalDateTime accountedAt,

        @NotBlank
        AccountStatus accountStatus
) {
}
