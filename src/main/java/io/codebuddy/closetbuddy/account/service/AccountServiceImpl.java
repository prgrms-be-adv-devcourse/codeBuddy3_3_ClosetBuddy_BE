package io.codebuddy.closetbuddy.account.service;

import io.codebuddy.closetbuddy.account.model.dto.AccountResponseDto;
import io.codebuddy.closetbuddy.account.model.entity.Account;
import io.codebuddy.closetbuddy.account.repository.AccountRepository;
import io.codebuddy.closetbuddy.member.entity.Member;
import io.codebuddy.closetbuddy.member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountService accountService;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;

    //예치금 조회
    /*
        1. 회원 조회
        2. 회원의 계좌 조회
        3. 등록된 예치금 조회

        account 부분에 대한 검증이 필요한지?
     */
    @Override
    @Transactional(readOnly=true)
    public AccountResponseDto getAccountBalance(Long memberId) {
        Optional<Member> memberOptional=memberRepository.findById(memberId);

        Member foundMember=memberOptional.orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        Account foundAccount=accountRepository.findByMember(foundMember);

        return new AccountResponseDto(foundMember.getId(),foundAccount.getBalance());

    }

    //예치금 등록
    @Override
    @Transactional
    public Account save(Long accountId) {

        Account account=Account.builder()
                .accountId(accountId)
                .build();


        return null;
    }

}
