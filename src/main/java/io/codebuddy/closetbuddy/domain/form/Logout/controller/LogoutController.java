package io.codebuddy.closetbuddy.domain.form.Logout.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

//로그아웃 컨트롤러
@Controller
@RequiredArgsConstructor
public class LogoutController {

    @PostMapping("/api/v1/auth/logout")
    public void logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
    }
}
