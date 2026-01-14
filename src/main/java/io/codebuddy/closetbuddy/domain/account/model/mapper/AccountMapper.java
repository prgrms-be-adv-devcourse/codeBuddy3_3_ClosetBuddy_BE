package io.codebuddy.closetbuddy.domain.account.model.mapper;

import io.codebuddy.closetbuddy.domain.account.model.dto.PaymentSuccessDto;
import io.codebuddy.closetbuddy.domain.account.model.entity.Account;
import io.codebuddy.closetbuddy.domain.account.model.entity.AccountHistory;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountChargeResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountResponse;

public class AccountMapper {

    public static AccountResponse toResponse(Account account, String message) {
        return new AccountResponse(
                account.getMember().getId(),
                account.getBalance(),
                message
        );
    }

    public static AccountChargeResponse toChargeResponse(PaymentSuccessDto paymentSuccessDto, Account account, AccountHistory accountHistory){
        return new AccountChargeResponse(
                paymentSuccessDto.getTotalAmount(),
                account.getBalance(),
                accountHistory.getAccountedAt(),
                accountHistory.getAccountStatus()
        );
    }
}
