package io.codebuddy.closetbuddy.domain.account.Login.controller;

import io.codebuddy.closetbuddy.domain.account.signup.dto.UserReqDTO;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.account.Login.security.auth.MemberPrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;


//    //SecurityContext 에서 인증 세부 정보를 가져 와서 사용자가 로그인했는지 확인(웹 사이트에서 사용자가 이미 로그인 한 상태에서 로그인 페이지를 방문하지 못하도록 하기 위함)
//    private boolean isAuthenticated() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //SecurityContext에서 현재 인증 객체를 가져옴
//        if (authentication == null || AnonymousAuthenticationToken.class. //AnonymousAuthenticationToken이면 익명 사용자(로그인 안함)
//                isAssignableFrom(authentication.getClass())) {
//            return false;
//        }
//        return authentication.isAuthenticated(); //isAuthenticated() 메서드는 항상 true를 반환
//    }



    @PostMapping("/auth/login")
    public ResponseEntity<?> login(HttpSession session, @RequestBody UserReqDTO userReqDTO) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userReqDTO.getUsername(), userReqDTO.getPassword())
            );

            MemberPrincipalDetails userDetails = (MemberPrincipalDetails) auth.getPrincipal(); //UserDetails를 구현한 객체가 가지고 있는 정보들을 가지고 옴
            session.setAttribute("loggedInUser", userDetails.getUsername());
            session.setAttribute("loginErrorMessage","");

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login success");
            response.put("userId", userDetails.getUsername());
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "잘못된 아이디 또는 비밀번호입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (DisabledException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "비활성 계정입니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        } catch (AuthenticationException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "인증 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

}
