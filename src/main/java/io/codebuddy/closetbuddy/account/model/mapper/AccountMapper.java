package io.codebuddy.closetbuddy.account.model.mapper;

import io.codebuddy.closetbuddy.account.model.entity.Account;
import io.codebuddy.closetbuddy.account.model.vo.AccountResponse;

public class AccountMapper {

    public static AccountResponse toResponse(Account account, String message) {
        return new AccountResponse(
                account.getMember().getId(),
                account.getBalance(),
                message
        );
    }
}
