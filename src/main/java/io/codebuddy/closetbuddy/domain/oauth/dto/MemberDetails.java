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
public class MemberDetails implements OAuth2User {

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

    public static MemberDetails from(Member member) {
        MemberDetails memberDetails = new MemberDetails();

        memberDetails.id = member.getId();

        memberDetails.email = member.getEmail();

        memberDetails.role = member.getRole();

        return memberDetails;
    }


    @Builder
    public MemberDetails(String name,String email, Map<String, Object> attributes) {
        this.name = name;
        this.email = email;
        this.attributes = attributes;
    }

}