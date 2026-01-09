package io.codebuddy.closetbuddy.domain.auth.config;

import io.codebuddy.closetbuddy.domain.auth.dto.MemberDetails;
import io.codebuddy.closetbuddy.domain.auth.dto.TokenBody ;
import io.codebuddy.closetbuddy.domain.auth.app.JwtTokenProvider;
import io.codebuddy.closetbuddy.domain.auth.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// 4. 요청마다 토큰 검증
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if ( token != null && jwtTokenProvider.validate(token) ) {

            TokenBody tokenBody = jwtTokenProvider.parseJwt(token);
            MemberDetails memberDetails = memberService.getMemberDetailsById(tokenBody.getMemberId());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    memberDetails, token, memberDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication); //SecurityContextHolder에 인증 저장

        }

        filterChain.doFilter(request, response);

    }

    private String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if ( bearerToken != null && bearerToken.startsWith("Bearer ") ) {
            return bearerToken.substring(7);
        }

        return null;

    }


}
