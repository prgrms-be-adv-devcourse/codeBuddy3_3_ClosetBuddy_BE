package io.codebuddy.closetbuddy.domain.account.service;


import io.codebuddy.closetbuddy.domain.account.model.dto.AccountCommand;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountResponse;

public interface AccountService {

    AccountResponse getAccountBalance(Long memberId);

    AccountResponse charge(AccountCommand command);


}
