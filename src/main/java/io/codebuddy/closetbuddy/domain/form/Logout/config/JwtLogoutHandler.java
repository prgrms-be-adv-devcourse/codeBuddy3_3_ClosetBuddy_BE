package io.codebuddy.closetbuddy.domain.form.Logout.config;

import io.codebuddy.closetbuddy.domain.oauth.repository.TokenRepository;
import io.codebuddy.closetbuddy.domain.form.Login.security.auth.MemberPrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

// 로그아웃 핸들러
@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;
    // 로그아웃할 때 특정 사용자의 유효한 Refresh token을 찾아서 있으면 refresh token을 없애고 blacklist에 넣는다.
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        // 1. refresh token을 "헤더"로 받는 경우
        if (authentication == null || !(authentication.getPrincipal() instanceof MemberPrincipalDetails memberPrincipalDetails)) {
            return;
        }

        Long memberId = memberPrincipalDetails.getMember().getId();
        tokenRepository.findValidRefToken(memberId).ifPresent(rt -> {tokenRepository.addBlackList(rt);
        tokenRepository.delete(rt);
        });
    }
}
