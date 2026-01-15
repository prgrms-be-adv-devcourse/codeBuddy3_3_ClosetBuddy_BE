package io.codebuddy.closetbuddy.domain.account.model.mapper;

import io.codebuddy.closetbuddy.domain.account.model.dto.PaymentSuccessDto;
import io.codebuddy.closetbuddy.domain.account.model.entity.Account;
import io.codebuddy.closetbuddy.domain.account.model.entity.AccountHistory;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountChargeResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountHistoryResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    // Account(entity) -> AccountResponse(vo)
    public static AccountResponse toResponse(Account account, String message) {
        return new AccountResponse(
                account.getMember().getId(),
                account.getBalance(),
                message
        );
    }


    // AccountHistory(Entity) -> AccountHistoryResponse(DTO)
    public static AccountHistoryResponse toHistoryResponse(AccountHistory history) {
        return new AccountHistoryResponse(
                history.getAmount(),
                history.getCreatedAt(),
                history.getType(),
                history.getBalanceSnapshot()
        );
    }

    // List<AccountHistory> -> List<AccountHistoryResponse>
    public static List<AccountHistoryResponse> toHistoryResponseList(List<AccountHistory> historyList) {
        if (historyList == null || historyList.isEmpty()) {
            return Collections.emptyList();
        }
        List<AccountHistoryResponse> responseList = new ArrayList<>();

        for (AccountHistory history : historyList) {
            responseList.add(toHistoryResponse(history));
        }

        return responseList;
    }
}
