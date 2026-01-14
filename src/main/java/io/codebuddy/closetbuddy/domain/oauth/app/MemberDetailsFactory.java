package io.codebuddy.closetbuddy.domain.oauth.app;

import io.codebuddy.closetbuddy.domain.oauth.dto.MemberPrincipalDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

//컴파일 경고를 사용하지 않도록 설정해주는 것.
//한마디로 노란색 표시줄이 나타내는 것 즉, 경고를 제외시킬 때 사용한다.
@SuppressWarnings({"all", "all"})
public class MemberDetailsFactory {
    public static MemberPrincipalDetails fromGoogle(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Google attributes에서 정보 추출
        return MemberPrincipalDetails.builder()
                .name((String) attributes.get("name"))      // Google의 name
                .email((String) attributes.get("email"))    // Google의 email
                .attributes(attributes)
                .build();
    }
}