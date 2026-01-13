package io.codebuddy.closetbuddy.account.service;

import io.codebuddy.closetbuddy.account.model.dto.AccountCommand;
import io.codebuddy.closetbuddy.account.model.vo.AccountResponse;
import io.codebuddy.closetbuddy.account.model.vo.TossPaymentConfirm;

public interface AccountService {

    AccountResponse getAccountBalance(Long memberId);

    AccountResponse charge(AccountCommand command);

    boolean confirmTossPayment(TossPaymentConfirm confirmReq);

}
