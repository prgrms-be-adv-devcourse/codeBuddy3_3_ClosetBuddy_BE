package io.codebuddy.closetbuddy.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private Long memberId;
    private Long balance;

}
