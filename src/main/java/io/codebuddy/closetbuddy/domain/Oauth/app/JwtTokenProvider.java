package io.codebuddy.closetbuddy.domain.Oauth.app;

import io.codebuddy.closetbuddy.domain.Oauth.Entity.RefreshToken ;
import io.codebuddy.closetbuddy.domain.common.model.dto.Role;
import io.codebuddy.closetbuddy.domain.Oauth.dto.TokenBody ;
import io.codebuddy.closetbuddy.domain.Oauth.dto.TokenPair ;
import io.codebuddy.closetbuddy.domain.Oauth.repository.TokenRepository ;
import io.codebuddy.closetbuddy.domain.Oauth.config.JwtConfiguration ;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

//3. 토큰 생성/검증/파싱
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JwtTokenProvider {
    // access Token과 Refresh 토큰의 재발급 정보를 담당
    private final JwtConfiguration jwtConfiguration;

    //refresh Token의 발급, 조회, 블랙 리스트 등록을 담당 -> 어댑터를 통해 서로 다른 두 인터페이스 사이에 통신이 가능하도록 해주었기 때문에 TokenRepository에서 한 번에 실행 가능
    private final TokenRepository tokenRepository;

    //토큰을 두개를 묶는다.
    public TokenPair generateTokenPair(Member member) {

        String accessToken = issueAccessToken(member.getId(), member.getRole());
        String refreshToken = issueRefreshToken(member.getId(), member.getRole());

        tokenRepository.save(member, refreshToken);

        return TokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 특정 사용자의 유효한 RefreshToken이 DB에 있는지 확인
    public Optional<RefreshToken> findRefreshToken(Long memberId) {
        return tokenRepository.findValidRefToken(memberId);
    }

    // Access 토큰 생성
    public String issueAccessToken(Long id, Role role) {
        return issue(id, role, jwtConfiguration.getValidation().getAccess());
    }

    // Refresh 토큰 생성
    public String issueRefreshToken(Long id, Role role) {
        return issue(id, role, jwtConfiguration.getValidation().getRefresh());
    }

    // jwtToken 생성
    private String issue(Long id, Role role, Long expTime) {
        // Payload = subject, claim("role"), issuedAt(iat), expiration(exp)
        // Signature = signWith를 통해 생성
        // 아래의 코드에는 header에 해당되는 코드가 없는데 자동적으로 생성된다.  (alg: HS256, typ: JWT)
        return Jwts.builder()
                .subject(id.toString()) // subject: 사용자 ID
                .claim("role", role) // 사용자 역할(권한)을 추가
                .issuedAt(new Date()) // 발급 시간 (iat) 현재 시간
                .expiration(new Date(new Date().getTime() + expTime)) // 만료 시간 (ext) 현재 시간 + yml 설정 시간
                .signWith(getSecretKey(), Jwts.SIG.HS256) // 시크릿 키로 서명하여 Signature 생성
                .compact(); //  Header + Payload + Signature 결합 → 최종 JWT 문자열 반환
    }

    // 클라이언트가 보낸 JWT의 유효성을 서명(Signature) 기반으로 검증한다.
    // 파싱: 데이터를 잘 알려진 일관된 형식에서 프로그램이 목적에 맞게 사용할 수 있는 형식으로 분해하는 과정입니다.
    // - parser(): JWT 문자열을 파싱할 준비를 한다.
    // - verifyWith(): 서명 검증에 사용할 SecretKey를 설정한다. (구버전은 setSigningKey() 사용 → 허용 타입 제한적)
    // - parseSignedClaims(): 토큰을 header.payload.signature로 분리하고, 서명이 유효한지 확인한다.
    public boolean validate(String token) {

        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        // JWT 관련 최상위 예외	만료, 위조, 포맷 문제 등 대부분의 JWT 검증 실패 시 발생
        } catch ( JwtException e ) {
            log.error("token = {}", token);
            log.error("JWT 토큰에 문제가 있습니다.");

        // Java 기본 예외	null이거나 빈 토큰이 들어온 경우
        } catch ( IllegalStateException e ) {
            log.error("token = {}", token);
            log.error("이상한 토큰이 검출되었습니다.");

        // 모든 나머지 예외 예상 못 한 오류 (시스템 오류 등)
        } catch (Exception e) {
            log.error("token = {}", token);
            log.error(";;");
        }

        return false;

    }

    //토큰 내부 정보를 파싱해서 사용자 ID (sub)와 역할 (role)을 꺼냄
    public TokenBody parseJwt(String token) {

        Jws<Claims> parsed = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token);

        String sub = parsed.getPayload().getSubject();
        String role = parsed.getPayload().get("role").toString();

        return new TokenBody(
                Long.parseLong(sub)
                // role이라는 커스텀 Claim을 가져온다.
                , Role.valueOf(role)
        );

    }

    // 시크릿 키 생성
    private SecretKey getSecretKey() {
        // JJWT 라이브러리의 유틸 클래스인 io.jsonwebtoken.security.Keys에서 제공하는 메서드로,HMAC 방식 서명을 위한 시크릿 키를 생성
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecrets().getAppKey().getBytes());
    }
}
