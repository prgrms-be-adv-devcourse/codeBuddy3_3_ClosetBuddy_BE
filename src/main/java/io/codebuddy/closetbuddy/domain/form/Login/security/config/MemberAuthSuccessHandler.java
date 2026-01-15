package io.codebuddy.closetbuddy.domain.form.Login.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codebuddy.closetbuddy.domain.oauth.app.JwtTokenProvider;
import io.codebuddy.closetbuddy.domain.oauth.dto.TokenPair;
import io.codebuddy.closetbuddy.domain.form.Login.security.auth.MemberPrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//로그인 성공했을 때의 핸들러
@Component
@RequiredArgsConstructor
public class MemberAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        MemberPrincipalDetails principal = (MemberPrincipalDetails) authentication.getPrincipal();
        TokenPair tokenPair = jwtTokenProvider.generateTokenPair(principal.getMember());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getOutputStream(), tokenPair); //TokenPair 객체가 {"accessToken":"...","refreshToken":"..."} 같은 JSON으로 변환되어 응답으로 내려갑니다.
    }
}
