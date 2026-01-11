package io.codebuddy.closetbuddy.domain.account.Login.controller;

import io.codebuddy.closetbuddy.domain.account.common.model.entity.LoginMember;
import io.codebuddy.closetbuddy.domain.account.Login.security.auth.MemberPrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1")
public class LoginController {

    //SecurityContext 에서 인증 세부 정보를 가져 와서 사용자가 로그인했는지 확인(웹 사이트에서 사용자가 이미 로그인 한 상태에서 로그인 페이지를 방문하지 못하도록 하기 위함)
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //SecurityContext에서 현재 인증 객체를 가져옴
        if (authentication == null || AnonymousAuthenticationToken.class. //AnonymousAuthenticationToken이면 익명 사용자(로그인 안함)
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated(); //isAuthenticated() 메서드는 항상 true를 반환
    }


    // 로그인 상태에 따라 적절한 페이지로 이동시키는 역할을 한다.
    @GetMapping("/auth/login")
    public String login(HttpServletRequest request,
                        @AuthenticationPrincipal MemberPrincipalDetails memberPrincipalDetails) {

        //에러 메시지 처리
        //이전 실패 메시지를 유지하면서 다음 요청에서 null 오류를 방지
        HttpSession session = request.getSession(); //HttpSession이 존재하면 현재 HttpSession을 반환. 기존 에러 메시지 조회
        String msg = (String) session.getAttribute("loginErrorMessage"); //지정한 이름(괄호 안 문자열)의 속성 값을 반환.msg가 null이면 빈 문자열로 설정.
        session.setAttribute("loginErrorMessage", msg != null ? msg : ""); //loginErrorMessage 속성 재설정 -> 로그인 폼에서 ${loginErrorMessage}로 표시

        //로그인 상태 체크
        if(isAuthenticated()) { //이미 로그인된 사용자
            if(memberPrincipalDetails == null)
                return "redirect: /auth/logout"; //현재 사용자 정보가 null이면 로그아웃 강제
            return "redirect:http://localhost:8084"; // 정상 사용자인 경우 메인으로 이동
        }

        return "login/login"; //html 파일 지정
    }

    //컨트롤러의 기본 경로에 main.html을 매핑
    @GetMapping("/")
    public String main() {
        return "main"; //임의의 html
    }

    //사용자의 정보를 템플릿에 전달
    @GetMapping("/member/text")
    public String text(@AuthenticationPrincipal MemberPrincipalDetails memberPrincipalDetails
            , Model model) {

        LoginMember member = memberPrincipalDetails.getMember();

        model.addAttribute("member", member);
        return "text/text"; //임의의 html
    }

}
