package io.codebuddy.closetbuddy.domain.oauth.service;

import io.codebuddy.closetbuddy.domain.oauth.dto.MemberPrincipalDetails;
import io.codebuddy.closetbuddy.domain.oauth.app.MemberDetailsFactory ;
import io.codebuddy.closetbuddy.domain.oauth.repository.MemberRepository ;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User = {}", oAuth2User);

        MemberPrincipalDetails memberPrincipalDetails = MemberDetailsFactory.fromGoogle(oAuth2User);

        Optional<Member> memberOptional = memberRepository.findByEmail(memberPrincipalDetails.getEmail());

        Member findMember = memberOptional.orElseGet(() -> {
            Member member = Member.builder()
                    .username(memberPrincipalDetails.getName())
                    .email(memberPrincipalDetails.getEmail())
                    .build();
            return memberRepository.save(member);
        });
        return memberPrincipalDetails.setId(findMember.getId()).setRole(findMember.getRole());
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Member getById(Long id) {
        return findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException()
                );
    }

    public MemberPrincipalDetails getMemberDetailsById(Long id) {
        Member findMember = getById(id);
        return MemberPrincipalDetails.from(findMember);
    }


}
