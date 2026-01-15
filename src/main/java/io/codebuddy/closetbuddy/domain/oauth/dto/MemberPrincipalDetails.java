package io.codebuddy.closetbuddy.domain.oauth.dto;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.codebuddy.closetbuddy.domain.common.model.dto.Role;

/*
MemberDetails 클래스는 Spring Security의 OAuth2User 인터페이스를 구현한 사용자 세부 정보 클래스이다. */
@Getter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPrincipalDetails implements OAuth2User {

    @Setter
    private Long id;

    private String name;

    private String email;

    @Setter
    private Role role;

    @Setter
    private Map<String, Object> attributes;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public static MemberPrincipalDetails from(Member member) {
        MemberPrincipalDetails memberPrincipalDetails = new MemberPrincipalDetails();

        memberPrincipalDetails.id = member.getId();

        memberPrincipalDetails.email = member.getEmail();

        memberPrincipalDetails.role = member.getRole();

        return memberPrincipalDetails;
    }


    @Builder
    public MemberPrincipalDetails(String name, String email, Map<String, Object> attributes) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
    }

}