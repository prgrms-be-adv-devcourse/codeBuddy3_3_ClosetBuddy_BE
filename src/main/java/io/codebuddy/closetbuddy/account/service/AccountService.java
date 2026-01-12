package io.codebuddy.closetbuddy.account.service;

import io.codebuddy.closetbuddy.account.model.dto.AccountResponseDto;
import io.codebuddy.closetbuddy.account.model.entity.Account;

public interface AccountService {

    Account save(Long accountId);

    AccountResponseDto getAccountBalance(Long memberId);

}
