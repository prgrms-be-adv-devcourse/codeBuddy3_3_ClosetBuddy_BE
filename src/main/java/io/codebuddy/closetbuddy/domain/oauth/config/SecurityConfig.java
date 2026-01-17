package io.codebuddy.closetbuddy.domain.oauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

//Spring Security의 보안 필터 체인을 정의하는 설정 클래스입니다. OAuth2 로그인을 활성화하고 모든 요청에 인증을 요구하는 기본 보안 정책을 설정합니다.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //1. SecurityFilterChain 빈으로 OAuth2 로그인과 JWT 필터 등록.

    /* Spring Security 설정을 통해 기존 인증 방식을 비활성화하고 API 엔드포인트에 대해 JWT 기반의 상태
    비저장 보안을 사용하는 OAuth2 로그인을 설정*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/oauth2/**","login/oauth2/**")
                .httpBasic(httpB -> httpB.disable())
                .csrf(csrf -> csrf.disable())
                .cors( cors -> cors.disable())
                .formLogin(form -> form.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2Login(Customizer.withDefaults())

                /*Spring의 OAuth2 필터가 활성화되어 내장 세션 저장소 없이도 권한 부여 코드 교환, 사용자 정보 검색 및 인증을 처리할 수 있습니다.
                 */
                .oauth2Login(oauth -> {
                    oauth.successHandler(oAuth2SuccessHandler);
                }) //OAuth2 로그인 활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        // 특정 경로는 모든 권한을 필요로 함
                        .requestMatchers("/member").hasAnyAuthority("MEMBER") //특정 경로일 경우 MEMBER 권한을 부여한다.(이건 추후에 수정 가능)
                        .requestMatchers("/admin").hasAnyAuthority("ADMIN") //특정 경로일 때 ADMIN 권한 부여
                        .requestMatchers("/guest").hasAnyAuthority("GUEST") //특정 경로일 때 GUEST 권한 부여.
                        //위에서 정의한 규칙 외 모든 엔드포인트 요청은 인증을 필요로 함.
                        .anyRequest()
                        .authenticated()
                    )
                // 사용자 정의 코드가 모든 요청의 헤더에서 JWT 토큰을 검증합니다. 폼 로그인이 비활성화되어 있으므로 사용자 이름/비밀번호 확인 단계를 건너뜁니다.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();// 필터 체인 빌드
    }
}
