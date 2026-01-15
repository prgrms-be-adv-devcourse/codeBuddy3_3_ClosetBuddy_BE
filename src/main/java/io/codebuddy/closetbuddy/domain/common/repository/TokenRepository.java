package io.codebuddy.closetbuddy.domain.common.repository;

import io.codebuddy.closetbuddy.domain.common.model.entity.RefreshTokenBlackList;
import io.codebuddy.closetbuddy.domain.common.model.entity.RefreshToken;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;

import java.util.Optional;


public interface TokenRepository {
    RefreshToken save(Member member, String token);
    RefreshTokenBlackList addBlackList(RefreshToken refreshToken);
    Optional<RefreshToken> findValidRefToken(Long memberId);
    void delete(RefreshToken refreshToken);
}
