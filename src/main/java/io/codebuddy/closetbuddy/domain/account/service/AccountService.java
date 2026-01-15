package io.codebuddy.closetbuddy.domain.account.service;


import io.codebuddy.closetbuddy.domain.account.model.dto.AccountCommand;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountChargeResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountHistoryResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse getAccountBalance(Long memberId);

    AccountHistoryResponse charge(AccountCommand command);

    List<AccountHistoryResponse> getHistoryAll(Long memberId);

    AccountHistoryResponse getHistory(Long memberId, Long historyId);

    AccountHistoryResponse deleteHistory(Long memberId, Long historyId, String reason);

}
