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

/*Authentication에는 User(사용자)정보가 있어야 한다.
MemberDetails 클래스는 Spring Security의 UserDetails 인터페이스를 구현한 사용자 세부 정보 클래스이다. 그래서 이 클래스는 사용자 인증과 관련된 정보를 제공하는 역할을 한다.
Authentication 타입 객체에는 User 정보를 저장한다.
Authentication 안에도 저장할 수 있는 객체의 타입이 정해져있다. 그것은 UserDetails 타입과 OAuth2User 타입이다. 이 둘 중 하나여야, Authentication 객체 안에 저장할 수 있다.
소셜로그인이 아닌 방식으로 로그인한 사용자 정보는 UserDetails 타입 객체로,
소셜로그인으로 로그인한 사용자 정보는 OAuth2User 타입 객체로 세션에 저장하게 된다.*/
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
    /*GrantedAuthority:Authentication에 부여한 권한을 나타낸다. 그냥 권한 그 자체로 생각
     * SimpleGrantedAuthority는 GrantedAuthority의 기본 구현체(클래스)다. 이 클래스는 Authentication 객체에 부여된 권한을 String으로 변환한다.
     * 정리
     * => GrantedAuthority는 Authentication(token ~ User(UserDetails))이 갖고 있는 권한을 String으로 표현한다.
     * 내가 사용자에게 admin 권한을 주고자 하면 ROLE_ADMIN으로 명명하여(또는 자동으로) SimpleGrantedAuthority 통해
     * 권한 명을 String으로 변환한 뒤 GrantedAuthority 타입의 객체에 저장한다.*/
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