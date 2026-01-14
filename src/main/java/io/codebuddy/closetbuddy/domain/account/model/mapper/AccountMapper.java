package io.codebuddy.closetbuddy.domain.account.model.mapper;

import io.codebuddy.closetbuddy.domain.account.model.entity.Account;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountResponse;

public class AccountMapper {

    public static AccountResponse toResponse(Account account, String message) {
        return new AccountResponse(
                account.getMember().getId(),
                account.getBalance(),
                message
        );
    }
}
