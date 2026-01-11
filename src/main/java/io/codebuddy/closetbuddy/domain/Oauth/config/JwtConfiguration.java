package io.codebuddy.closetbuddy.domain.Oauth.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtConfiguration {

    private final Validation validation;
    private final Secrets secrets;

    @Getter
    @RequiredArgsConstructor
    public static class Validation {
        private final Long access; //Access 토큰 만료시간 (밀리초)
        private final Long refresh; //Refresh 토큰 만료시간 (밀리초)
    }

    @Getter
    @RequiredArgsConstructor
    public static class Secrets {
        private final String originKey; //CORS origin 허용 키 (미사용으로 보임)
        private final String appKey; //JWT 서명 비밀키
    }


}
