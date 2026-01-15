package io.codebuddy.closetbuddy.domain.oauth.app;

import io.codebuddy.closetbuddy.domain.oauth.dto.MemberDetails ;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

//SuppressWarnings: 컴파일 경고를 사용하지 않도록 설정해주는 어노테이션.
// Google attributes에서 정보 추출(Google의 name, Google의 email)
@SuppressWarnings({"all", "all"})
public class MemberDetailsFactory {
    public static MemberDetails fromGoogle(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Google attributes에서 정보 추출
        return MemberDetails.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .build();
    }
}