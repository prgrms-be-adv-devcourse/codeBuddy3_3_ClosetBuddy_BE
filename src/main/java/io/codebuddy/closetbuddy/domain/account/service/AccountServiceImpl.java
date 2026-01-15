package io.codebuddy.closetbuddy.domain.account.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.codebuddy.closetbuddy.domain.account.model.dto.AccountCommand;
import io.codebuddy.closetbuddy.domain.account.model.dto.PaymentSuccessDto;
import io.codebuddy.closetbuddy.domain.account.model.dto.TossPaymentResponse;
import io.codebuddy.closetbuddy.domain.account.model.entity.Account;
import io.codebuddy.closetbuddy.domain.account.model.entity.AccountHistory;
import io.codebuddy.closetbuddy.domain.account.model.mapper.AccountMapper;
import io.codebuddy.closetbuddy.domain.account.model.vo.*;
import io.codebuddy.closetbuddy.domain.account.repository.AccountHistoryRepository;
import io.codebuddy.closetbuddy.domain.account.repository.AccountRepository;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.common.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final ObjectMapper om;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    @Value("${custom.payments.toss.secrets}")
    private String tossPaymentSecrets;

    @Value("${custom.payments.toss.url}")
    private String tossPaymentUrl;

    /**
     * 예치금 조회
     * @param memberId
     * @return 회원 아이디와 조회된 예치금을 리턴합니다.
     * 1. 회원 확인
     * 2. 예치금 계좌 확인
     * 3. 예치금 조회
     */
    @Override
    @Transactional(readOnly=true)
    public AccountResponse getAccountBalance(Long memberId) {

        Optional<Member> memberOptional=memberRepository.findById(memberId);

        Member foundMember=memberOptional.orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        Account account = accountRepository.findByMember(foundMember).orElse(Account.createAccount(foundMember));

        return AccountMapper.toResponse(account, "조회가 완료되었습니다.");
    }

    /**
     * 예치금 등록
     * @param command(meberId, 예치할 금액,paymentKey,orderId)
     * @return accountChargeResponse(예치한 금액,총 예치된 금액, 예치 일시, 예치 상태)
     *
     * 1. PG 결제 승인 요청 (먼저 수행하여 실패 시 db 접근 차단)
     * 2. 금액 검증
     * 3. 회원 검증
     * 4. 계좌 조회
     * 5. 예치금 충전
     * 6. 예치금 내역 저장
     *
     */
    @Override
    @Transactional
    public AccountChargeResponse charge(AccountCommand command) {

        // PG 결제 승인 요청
        PaymentSuccessDto paymentSuccessDto = confirmTossPayment(command);

        // 금액 검증
        // 요청한 금액과 실제 결제된 금액이 다르면 예외 발생
        if (!command.getAmount().equals(paymentSuccessDto.getTotalAmount()) ) {
            throw new IllegalStateException("요청 금액과 실제 결제 금액이 일치하지 않습니다.");
        }

        // 회원 검증
        Member foundMember = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 계좌 조회
        Account account = accountRepository.findByMember(foundMember)
                .orElseGet(() -> {
                    Account newAccount = Account.createAccount(foundMember);
                    return accountRepository.save(newAccount);
                });


        // 충전
        account.charge(command.getAmount());

        // 날짜 파싱 (String -> LocalDateTime)
        // 토스는 ISO 8601 (2022-01-01T00:00:00+09:00) 형식
        LocalDateTime approvedAt = OffsetDateTime.parse(paymentSuccessDto.getApprovedAt())
                .toLocalDateTime();

        // 예치금 내역 저장
        AccountHistory accountHistory = AccountHistory.builder()
                .account(account)
                .paymentKey(paymentSuccessDto.getPaymentKey())
                .orderId(command.getOrderId())
                .accountAmount(command.getAmount())
                .accountStatus(AccountStatus.DONE)
                .accountedAt(approvedAt)
                .build();

        accountHistoryRepository.save(accountHistory);

        return AccountMapper.toChargeResponse(paymentSuccessDto,account,accountHistory);
    }

    /**
     * 예치 내역 전체 조회
     * @param memberId
     * @return 예치 내역 전체 리스트(예치한 금액, 예치한 시각, 예치 상태)
     *
     * 1. 멤버 조회
     * 2. 계좌 조회
     * 3. 예치 내역 전체 조회 (최신순)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountHistoryResponse> getHistoryAll(Long memberId) {
        Optional<Member> memberOptional=memberRepository.findById(memberId);

        Member foundMember=memberOptional.orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        // 계좌 조회
        // 계좌가 아예 없다면 -> 내역도 없음 -> 빈 리스트 반환
        Account account = accountRepository.findByMember(foundMember)
                .orElse(null);

        if (account == null) {
            return Collections.emptyList();
        }

        // 예치 내역 전체 조회 (최신순)
        List<AccountHistory> historyList = accountHistoryRepository.findAllByAccountOrderByAccountedAtDesc(account);

        return AccountMapper.toHistoryResponseList(historyList);
    }


    /**
     *
     * 예치 내역 단건 조회
     * @param memberId
     * @param historyId
     * @return 예치 내역 (예치한 금액, 예치한 시각, 예치 상태)
     *
     * 1. 멤버 검증
     * 2. 계좌 조회
     * 3. 예치 내역 단건 조회
     *
     */
    @Override
    @Transactional(readOnly = true)
    public AccountHistoryResponse getHistory(Long memberId, Long historyId) {

        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Account account = accountRepository.findByMember(foundMember)
                .orElseThrow(() -> new IllegalArgumentException("계좌가 존재하지 않습니다."));

        // 내역 단건 조회
        // 계좌 객체(account)를 조건으로 넣어서, 남의 내역을 조회하는 것을 차단
        AccountHistory history = accountHistoryRepository.findByAccountAndAccountHistoryId(account, historyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 본인의 내역이 아닙니다."));

        return AccountMapper.toHistoryResponse(history);
    }

    /**
     * 특정 예치 내역 삭제
     * @param memberId
     * @param historyId
     * @param reason(환불 사유) - 토스 필수 request Body 파라미터
     *
     * 1. 멤버 검증
     * 2. 계좌 검증
     * 3. 내역 조회
     * 4. 이미 취소된 내역인지 검증
     * 5. 잔액 검증
     * 6. 계좌 잔액 차감
     * 7. 예치 내역 상태를 취소로 변경
     * 8. 토스 환불
     *
     */
    @Override
    @Transactional
    public void deleteHistory(Long memberId, Long historyId,String reason) {
        // 멤버 검증
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 계좌 검증
        Account account = accountRepository.findByMember(foundMember)
                .orElseThrow(() -> new IllegalArgumentException("계좌가 존재하지 않습니다."));

        // 내역 조회 (내 계좌의 내역인지 확인)
        AccountHistory history = accountHistoryRepository.findByAccountAndAccountHistoryId(account, historyId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 본인의 내역이 아닙니다."));

        // 이미 취소된 내역인지 검증
        if (history.getAccountStatus() == AccountStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 내역입니다.");
        }

        // 잔액 검증
        if (account.getBalance() < history.getAccountAmount()) {
            throw new IllegalStateException("잔액이 부족하여 취소할 수 없습니다.");
        }

        // 계좌 잔액 차감
        account.withdraw(history.getAccountAmount());

        // 예치 내역 상태 변경(취소)
        history.cancel();
        
        // 토스 환불 진행
        cancelTossPayment(history.getPaymentKey(), reason);
    }

    // 토스 결제 취소
    private void cancelTossPayment(String paymentKey, String cancelReason) {
        try {
            // 인증 헤더 (시크릿 키)
            String authorization = "Basic " + Base64.getEncoder()
                    .encodeToString((tossPaymentSecrets + ":").getBytes(StandardCharsets.UTF_8));

            // 요청 Body 생성 (Record -> JSON)
            String requestBody = om.writeValueAsString(new TossCancelRequest(cancelReason));

            // HttpClient 준비
            HttpClient client = HttpClient.newHttpClient();

            // 요청 만들기 (POST)
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(tossPaymentUrl + "/" + paymentKey + "/cancel"))
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 전송
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 결과 확인
            if (response.statusCode() != 200) {
                // 200이 아니면 실패로 간주하고 예외 발생 -> Transaction Rollback 유도
                log.error("토스 환불 실패. 상태코드: {}, 내용: {}", response.statusCode(), response.body());
                throw new RuntimeException("토스 결제 취소 실패: " + response.body());
            }

            log.info("토스 결제 취소 성공. paymentKey: {}", paymentKey);

        } catch (Exception e) {
            log.error("토스 결제 취소 중 통신 오류 발생", e);
            // 여기서 예외를 던져야 deleteHistory의 @Transactional이 작동해 DB도 롤백
            throw new RuntimeException("결제 취소 연동 중 오류가 발생했습니다.", e);
        }


    }


    // 토스 결제 검증
    private PaymentSuccessDto confirmTossPayment(AccountCommand command) {
        try {
            // 1. 시크릿 키 인코딩
            String authorization = "Basic " + Base64.getEncoder()
                    .encodeToString((tossPaymentSecrets + ":").getBytes(StandardCharsets.UTF_8));

            // 2. 요청 객체를 JSON 문자열로 변환
            String requestBody = om.writeValueAsString(
                    new TossPaymentConfirm(
                            command.getPaymentKey(),
                            command.getOrderId(),
                            command.getAmount()));

            // 3. HttpClient 생성
            HttpClient client = HttpClient.newHttpClient();

            // 4. 요청 생성
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(tossPaymentUrl + "/confirm"))
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 5. 요청 전송 및 응답 수신
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 6. 결과 처리
            if (response.statusCode() == 200) {
                // 성공 시: json 문자열 -> dto로 매핑
                TossPaymentResponse tossResponse = om.readValue(response.body(), TossPaymentResponse.class);
                return PaymentSuccessDto.builder()
                        .paymentKey(tossResponse.getPaymentKey())
                        .status(tossResponse.getStatus())
                        .totalAmount(tossResponse.getTotalAmount())
                        .approvedAt(tossResponse.getApprovedAt())
                        .build();
            } else {
                // 실패 시: 에러 로그 출력 및 예외 발생
                log.error("토스 결제 승인 실패. 응답코드: {}, 내용: {}", response.statusCode(), response.body());
                throw new RuntimeException("결제 승인 실패: " + response.body());
            }

        } catch (Exception e) {
            log.error("토스 결제 통신 중 에러 발생", e);
            throw new RuntimeException("결제 시스템 연동 중 오류가 발생했습니다.", e);
        }
    }
}
