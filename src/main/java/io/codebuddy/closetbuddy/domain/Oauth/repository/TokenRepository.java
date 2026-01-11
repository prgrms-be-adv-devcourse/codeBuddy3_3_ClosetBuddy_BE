package io.codebuddy.closetbuddy.domain.Oauth.repository;

import io.codebuddy.closetbuddy.domain.Oauth.Entity.Member ;
import io.codebuddy.closetbuddy.domain.Oauth.Entity.RefreshTokenBlackList ;
import io.codebuddy.closetbuddy.domain.Oauth.Entity.RefreshToken ;

import java.util.Optional;


public interface TokenRepository {
    RefreshToken save(Member member, String token);
    RefreshTokenBlackList addBlackList(RefreshToken refreshToken);
    Optional<RefreshToken> findValidRefToken(Long memberId);
}
