package io.codebuddy.closetbuddy.domain.oauth.repository;

import io.codebuddy.closetbuddy.domain.oauth.Entity.RefreshTokenBlackList ;
import io.codebuddy.closetbuddy.domain.oauth.Entity.RefreshToken ;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;

import java.util.Optional;


public interface TokenRepository {
    RefreshToken save(Member member, String token);
    RefreshTokenBlackList addBlackList(RefreshToken refreshToken);
    Optional<RefreshToken> findValidRefToken(Long memberId);
}
