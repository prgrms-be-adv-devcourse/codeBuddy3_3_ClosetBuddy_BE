package io.codebuddy.closetbuddy.domain.auth.repository;

import io.codebuddy.closetbuddy.domain.auth.Entity.Member ;
import io.codebuddy.closetbuddy.domain.auth.Entity.RefreshTokenBlackList ;
import io.codebuddy.closetbuddy.domain.auth.Entity.RefreshToken ;

import java.util.Optional;


public interface TokenRepository {
    RefreshToken save(Member member, String token);
    RefreshTokenBlackList addBlackList(RefreshToken refreshToken);
    Optional<RefreshToken> findValidRefToken(Long memberId);
}
