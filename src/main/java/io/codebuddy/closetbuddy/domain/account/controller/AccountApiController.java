package io.codebuddy.closetbuddy.domain.account.controller;

import io.codebuddy.closetbuddy.domain.account.model.dto.AccountCommand;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountChargeResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountHistoryResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.AccountResponse;
import io.codebuddy.closetbuddy.domain.account.model.vo.PaymentConfirmRequest;
import io.codebuddy.closetbuddy.domain.account.service.AccountServiceImpl;
import io.codebuddy.closetbuddy.domain.form.Login.security.auth.MemberPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountServiceImpl accountService;

    //예치금 조회
    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getAccountBalance(
            @AuthenticationPrincipal MemberPrincipalDetails principal
    ){
        if (principal == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        // principal에서 Member 객체를 꺼내고 -> ID를 가져옴
        Long memberId = principal.getMember().getId();
        AccountResponse accountResponse = accountService.getAccountBalance(memberId);
        return ResponseEntity.ok(accountResponse);
    }

    //예치금 등록
    @PostMapping("/charge")
    public ResponseEntity<AccountChargeResponse> chargeAccount(
            @AuthenticationPrincipal MemberPrincipalDetails principal,
            @RequestBody PaymentConfirmRequest request
    ) {

        Long memberId = principal.getMember().getId();

        AccountCommand command = new AccountCommand(
                memberId,
                request.amount(),
                request.orderId(),
                request.paymentKey()
        );
        AccountChargeResponse response = accountService.charge(command);

        return ResponseEntity.ok(response);
    }

    // 예치 내역 전체 조회
    @GetMapping("/history")
    public ResponseEntity<List<AccountHistoryResponse>> getAccountHistory(
            @AuthenticationPrincipal MemberPrincipalDetails principal
    ) {
        Long memberId = principal.getMember().getId();

        List<AccountHistoryResponse> historyList = accountService.getHistoryAll(memberId);

        return ResponseEntity.ok(historyList);
    }

    // 예치금 내역 상세(단건) 조회
    @GetMapping("/history/{accountHistoryId}")
    public ResponseEntity<AccountHistoryResponse> getAccountHistoryDetail(
            @AuthenticationPrincipal MemberPrincipalDetails principal,
            @PathVariable Long accountHistoryId
    ) {

        Long memberId = principal.getMember().getId();

        AccountHistoryResponse response = accountService.getHistory(memberId, accountHistoryId);

        return ResponseEntity.ok(response);
    }




}
