package io.codebuddy.closetbuddy.domain.oauth.config;

import io.codebuddy.closetbuddy.domain.oauth.dto.MemberDetails ;
import io.codebuddy.closetbuddy.domain.oauth.dto.TokenPair ;
import io.codebuddy.closetbuddy.domain.oauth.app.JwtTokenProvider ;
import io.codebuddy.closetbuddy.domain.oauth.service.MemberService ;
import io.codebuddy.closetbuddy.domain.oauth.Entity.RefreshToken ;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

// 2. OAuth2 로그인 성공 시 JWT 토큰 발급
// OAuth2 로그인 성공 시 JWT 토큰을 발급하고 프론트엔드로 리다이렉트하는 핸들러입니다.
// SimpleUrlAuthenticationSuccessHandler는 로그인 성공 시 redirect할 경로를 설정하거나, 이후 어떤 처리를 할 지 설정한다.
// OAuth2SuccessHandler 클래스는 로그인에 성공했을 때 동작할 핸들러를 만들어줘야하기 때문에 아래를 구현한 것이다.
@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    // Value 안에는 OAuth2 로그인 성공 후 리다이렉트할 기본 URL 경로를 넣으면 된다.

    @Value("${custom.jwt.redirection.base}")
    private String baseUrl;

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // OAuth2User에서 회원 ID 추출 후 DB에서 회원 정보 조회.
        MemberDetails principal = (MemberDetails) authentication.getPrincipal();//현재 로그인한 사용자의 정보를 가져옴
        Member findMember = memberService.getById(principal.getId());//회원 ID를 추출한 후 DB에서 회원 정보를 조회.
        // 조회하는 이유는 OAuth 제공자(Google 등)가 주는 제한된 정보만으로는 충분하지 않기 때문

        HashMap<String, String> params = new HashMap<>();
        //Refresh 토큰 확인 및 토큰 발급 전략
        Optional<RefreshToken> refreshTokenOptional = jwtTokenProvider.findRefreshToken(principal.getId());
        if ( refreshTokenOptional.isEmpty() ) {
            // 신규 로그인: Access + Refresh 토큰 쌍 발급
            TokenPair tokenPair = jwtTokenProvider.generateTokenPair(findMember);
            params.put("access", tokenPair.getAccessToken());
            params.put("refresh", tokenPair.getRefreshToken());
        } else {
            // 기존 Refresh 토큰 있음: Access 토큰만 재발급
            String accessToken = jwtTokenProvider.issueAccessToken(principal.getId(), principal.getRole());
            params.put("access", accessToken);
            params.put("refresh", refreshTokenOptional.get().getRefreshToken());
        }

        // 리다이렉트 URL 생성
        String urlStr = genUrlStr(params); //리다이렉트할 URL을 문자열로 만들어주는 메서드
        getRedirectStrategy().sendRedirect(request, response, urlStr); //리디렉션 전략을 반환하는 메서드이다.
        // 사용자 혹은 클라이언트를 urlStr로 리디렉션하며, request, response 객체를 넘겨준다.

    }

    // OAuth2 로그인 성공 후 JWT 토큰을 포함한 리다이렉트 URL을 안전하게 생성하는 메서드
    private String genUrlStr(HashMap<String, String> params) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("access", params.get("access"))
                .queryParam("refresh", params.get("refresh"))
                .build()
                .toUri()
                .toString();
    }

}
