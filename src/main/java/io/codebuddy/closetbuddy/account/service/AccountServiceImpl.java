package io.codebuddy.closetbuddy.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codebuddy.closetbuddy.account.model.dto.AccountCommand;
import io.codebuddy.closetbuddy.account.model.entity.Account;
import io.codebuddy.closetbuddy.account.model.mapper.AccountMapper;
import io.codebuddy.closetbuddy.account.model.vo.AccountResponse;
import io.codebuddy.closetbuddy.account.model.vo.TossPaymentConfirm;
import io.codebuddy.closetbuddy.account.repository.AccountRepository;
import io.codebuddy.closetbuddy.member.entity.Member;
import io.codebuddy.closetbuddy.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final ObjectMapper om;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;

    @Value("${custom.payments.toss.secrets}")
    private String tossPaymentSecrets;

    @Value("${custom.payments.toss.confirm-url}")
    private String tossPaymentConfirmUrl;

    //예치금 조회
    /*
        1. 회원 조회
        2. 회원의 계좌 조회
        3. 등록된 예치금 조회

        account 생성에 대한 검증이 필요한지?
     */
    @Override
    @Transactional(readOnly=true)
    public AccountResponse getAccountBalance(Long memberId) {
        Optional<Member> memberOptional=memberRepository.findById(memberId);

        Member foundMember=memberOptional.orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        Account account=accountRepository.findByMember(foundMember);

        return AccountMapper.toResponse(account, "조회가 완료되었습니다.");

    }

    //예치금 등록
    /*
        1- PG 결제 검증
        2- 회원 검증
        3- 예치금 등록
     */
    @Override
    @Transactional
    public AccountResponse charge(AccountCommand command) {

        // pg사 결제 검증
        boolean isVerified = confirmTossPayment(
                new TossPaymentConfirm(command.getPaymentKey(), command.getOrderId(), command.getAmount())
        );

        if (!isVerified) {
            throw new IllegalStateException("PG사 결제 검증에 실패했습니다.");
        }

        // 회원 검증
        Optional<Member> memberOptional=memberRepository.findById(command.getMemberId());

        Member foundMember=memberOptional.orElseThrow(
                ()->new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        Account account=accountRepository.findByMember(foundMember);

        // 충전
        account.charge(command.getAmount());

        return AccountMapper.toResponse(account, "충전이 완료되었습니다.");
    }

    // PG 결제 검증
    @Override
    public boolean confirmTossPayment(TossPaymentConfirm request) {
        try {
            // 인증 헤더 생성(Base64)
            String authorization = "Basic " + Base64.getEncoder()
                    .encodeToString((tossPaymentSecrets + ":").getBytes(StandardCharsets.UTF_8));

            // 요청 Body
            Map<String, Object> requestMap = om.convertValue(request, new TypeReference<>() {
            });

            // http 요청 객체
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(tossPaymentConfirmUrl))
                    .header("Authorization", authorization)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(om.writeValueAsBytes(requestMap)))
                    .build();

            // 요청 전송 및 응답 수신
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 결과 확인
            if (response.statusCode() == HttpStatus.OK.value()) {
                return true;
            } else {
                log.error("토스페이먼츠 결제 수행 과정에서 오류가 발생하였습니다. 다시 시도하여 주시기 바랍니다. 응답코드 : {}", response.statusCode());
                log.info("response.body() = {}", response.body());
                return false;
            }
        } catch (Exception e) {
            log.error("토스페이먼츠 결제 수행 과정에서 오류가 발생하였습니다.");
            return false;
        }
    }
}
