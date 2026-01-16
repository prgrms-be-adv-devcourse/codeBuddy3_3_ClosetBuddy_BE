package io.codebuddy.closetbuddy.domain.member.service;

import io.codebuddy.closetbuddy.domain.common.model.dto.Role;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.common.repository.MemberRepository;
import io.codebuddy.closetbuddy.domain.member.model.dto.MemberResponse;
import io.codebuddy.closetbuddy.domain.member.model.dto.MemberUpdateRequest;
import io.codebuddy.closetbuddy.domain.member.model.dto.SellerRegisterRequest;
import io.codebuddy.closetbuddy.domain.common.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public MemberResponse getMe(Long memberId) {
        Member m = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));
        return new MemberResponse(m.getId(),
                m.getMemberId(),
                m.getUsername(),
                m.getEmail(),
                m.getAddress(),
                m.getPhone(),
                m.getRole().name());
    }

    public MemberResponse updateMe(Long memberId, MemberUpdateRequest req) {
        Member m = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));

        if (req.name() != null) m.setUsername(req.name());
        if (req.email() != null) m.setEmail(req.email());
        if (req.phone() != null)   m.setPhone(req.phone());
        if (req.address() != null) m.setAddress(req.address());

        return new MemberResponse(m.getId(),
                m.getMemberId(),
                m.getUsername(),
                m.getEmail(),
                m.getAddress(),
                m.getPhone(),
                m.getRole().name());
    }

    public void deleteMe(Long memberId) {
        refreshTokenRepository.deleteAllByMember_Id(memberId); // 1) 자식 먼저 삭제
        memberRepository.deleteById(memberId); // 2) 부모 삭제
    }

    public MemberResponse registerSeller(Long memberId, SellerRegisterRequest req) {
        Member m = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("member not found"));

        // 예: role 변경
        m.setRole(Role.SELLER);

        return new MemberResponse(m.getId(),
                m.getMemberId(),
                m.getUsername(),
                m.getEmail(),
                m.getAddress(),
                m.getPhone(),
                m.getRole().name());
    }
}
