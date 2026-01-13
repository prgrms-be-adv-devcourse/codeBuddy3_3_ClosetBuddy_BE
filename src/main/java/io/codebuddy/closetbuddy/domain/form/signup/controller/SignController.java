package io.codebuddy.closetbuddy.domain.form.signup.controller;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.common.model.dto.UserReqDTO;
import io.codebuddy.closetbuddy.domain.form.signup.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1")
public class SignController {

    private final SignService signService;
    //회원가입
    @PostMapping("/authc")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Member> create(@RequestBody UserReqDTO userReqDTO) {
        Member saved = signService.create(userReqDTO);
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(saved);
    }

}
