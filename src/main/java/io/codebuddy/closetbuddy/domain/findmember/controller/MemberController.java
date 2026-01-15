package io.codebuddy.closetbuddy.domain.findmember.controller;

import io.codebuddy.closetbuddy.domain.findmember.dto.MemberResponse;
import io.codebuddy.closetbuddy.domain.findmember.dto.MemberUpdateRequest;
import io.codebuddy.closetbuddy.domain.findmember.dto.SellerRegisterRequest;
import io.codebuddy.closetbuddy.domain.findmember.service.findService;
import io.codebuddy.closetbuddy.domain.oauth.dto.MemberPrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final findService memberCommandService;

    //회원 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@AuthenticationPrincipal MemberPrincipalDetails principal) {
        return ResponseEntity.ok(memberCommandService.getMe(principal.getId()));
    }

    //회원 정보 수정
    @PatchMapping("/me")
    public ResponseEntity<MemberResponse> updateMe(@AuthenticationPrincipal MemberPrincipalDetails principal,
                                                   @RequestBody MemberUpdateRequest req) {
        return ResponseEntity.ok(memberCommandService.updateMe(principal.getId(), req));
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal MemberPrincipalDetails principal) {
        memberCommandService.deleteMe(principal.getId());
        return ResponseEntity.noContent().build();
    }

    //판매자 전환
    @PostMapping("/me/seller")
    public ResponseEntity<MemberResponse> registerSeller(@AuthenticationPrincipal MemberPrincipalDetails principal,
                                                         @RequestBody SellerRegisterRequest req) {
        return ResponseEntity.ok(memberCommandService.registerSeller(principal.getId(), req));
    }
}
