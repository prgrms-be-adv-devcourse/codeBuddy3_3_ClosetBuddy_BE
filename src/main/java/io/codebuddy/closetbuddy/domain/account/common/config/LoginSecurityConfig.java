package io.codebuddy.closetbuddy.domain.account.common.config;

import io.codebuddy.closetbuddy.domain.account.Login.security.auth.MemberPrincipalDetailService;
import io.codebuddy.closetbuddy.domain.account.Login.security.config.MemberAuthFailureHandler;
import io.codebuddy.closetbuddy.domain.account.Login.security.config.MemberAuthSuccessHandler;
import io.codebuddy.closetbuddy.domain.account.Login.security.provider.MemberAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class LoginSecurityConfig {

    // 생성해둔 MemberAuthenticatorProvider를 주입받는다.
    // 해당 클래스로 MemberPrincipalDetailsService 내부 로직을 수행하며
    // 인증 처리도 같이 진행된다
    @Autowired
    MemberAuthenticationProvider memberAuthenticatorProvider;

    // 로그인 기억하고 사용을 하기 위해 MemberAuthenticatorProvider 내부에 MemberPrincipalDetailsService 선언
    @Autowired
    MemberPrincipalDetailService memberPrincipalDetailService;

    // in memory 방식으로 인증 처리를 진행 하기 위해 기존엔 Override 하여 구현했지만
    // Spring Security 5.7.0 버전부터는 AuthenticationManagerBuilder를 직접 생성하여
    // AuthenticationManager를 생성해야 한다.
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(memberAuthenticatorProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 생성해둔 MemberAuthenticatorProvider를 주입받는다.
        // 해당 클래스로 MemberPrincipalDetailsService 내부 로직을 수행하며
        // 인증 처리도 같이 진행된다


        http
                .authorizeHttpRequests((requests) -> requests //authorizeHttpRequests: 권한을 설정해주는 메소드
                        .requestMatchers("/", "/api/v1/authc","/api/v1/auth/login").permitAll()// requestMatchers: 요청 타입에 따라 URL 패턴을 지정하여 해당 요청 타입에 대한 보안 설정을 할 때 사용됩니다.
                        .anyRequest().authenticated() //그외에는 인증 필요
                )
                .csrf(csrf -> csrf.disable())

                /*로그인 설정
                formLogin의 작동 방식(회원가입 완료 이후)
                * 1. 사용자가 Server에 특정 URL을 요청하였을 때 해당 URL이 인증이 필요할 경우 Server는 Login 페이지를 return하게 됩니다.
                  2. 사용자는 ID와 password를 입력하여 로그인 요청을 하면 Post mapping으로 해당 데이터가 서버에 전송됩니다.
                  3. Server는 해당 로그인 정보를 확인합니다. 해당 유저 정보가 존재한다면 Session과 Token을 생성하고 저장해둡니다.*/
                .formLogin((form) -> form
                        .loginPage("/api/v1/auth/login") // 로그인 페이지 설정
                        .loginProcessingUrl("/api/v1/auth/login/login") // 로그인 처리 URL 설정
                        .successHandler(new MemberAuthSuccessHandler()) // 로그인 성공 후 처리할 핸들러
                        .failureHandler(new MemberAuthFailureHandler()) // 로그인 실패 후 처리할 핸들러
                        .permitAll()
                )

                .logout((logout) -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessUrl("/api/v1/auth/loginForm?logout=1")
                        .deleteCookies("JSESSIONID") //로그아웃 후 쿠키 삭제. JSESSIONID이란 세션 쿠키 방식으로 로그인을 인증할 때 사용된다.
                );

        //Remember-Me는 사용자가 "로그인 상태 유지" 체크박스를 선택했을 때, 브라우저를 닫았다가 다시 열어도 자동으로 로그인 상태를 유지해주는 기능

        http.rememberMe(rememberMe -> rememberMe
                .key("my-super-secret-key-2026") // 인증 토큰 생성시 사용할 키
                .tokenValiditySeconds(60 * 60 * 24 * 7) // 인증 토큰 유효 시간 (초)
                .userDetailsService(memberPrincipalDetailService) // 기능을 사용할 때 사용자 정보가 필요함. 인증 토큰 생성시 사용할 UserDetailsService
                .rememberMeParameter("remember-me")); // 로그인 페이지에서 사용할 파라미터 이름)


        return http.build();

        }
    // 패스워드 암호화로 사용할 bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
