package io.codebuddy.closetbuddy.account.controller;

import io.codebuddy.closetbuddy.account.model.dto.AccountResponseDto;
import io.codebuddy.closetbuddy.account.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class AccountApiController {

    private final AccountServiceImpl accountService;

    //예치금 조회
    //
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccountBalance(
            @PathVariable Long accountId
    ){
        AccountResponseDto accountResponseDto= accountService.getAccountBalance(accountId);
        return ResponseEntity.ok(accountResponseDto);
    }

    //예치금 등록




}
