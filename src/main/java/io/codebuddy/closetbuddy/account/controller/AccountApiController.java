package io.codebuddy.closetbuddy.account.controller;

import io.codebuddy.closetbuddy.account.model.dto.AccountCommand;
import io.codebuddy.closetbuddy.account.model.vo.PaymentConfirmRequest;
import io.codebuddy.closetbuddy.account.model.vo.AccountResponse;
import io.codebuddy.closetbuddy.account.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deposit")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountServiceImpl accountService;

    //예치금 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<AccountResponse> getAccountBalance(
            @PathVariable Long memberId
    ){
        AccountResponse accountResponse = accountService.getAccountBalance(memberId);
        return ResponseEntity.ok(accountResponse);
    }

    //예치금 등록
    @PostMapping("/{memberId}")
    public ResponseEntity<AccountResponse> chargeDeposit(
            @PathVariable Long memberId,
            @RequestBody PaymentConfirmRequest request
    ) {
        AccountCommand command = new AccountCommand(
                memberId,
                request.amount(),
                request.orderId(),
                request.paymentKey()
        );
        AccountResponse response = accountService.charge(command);

        return ResponseEntity.ok(response);
    }




}
