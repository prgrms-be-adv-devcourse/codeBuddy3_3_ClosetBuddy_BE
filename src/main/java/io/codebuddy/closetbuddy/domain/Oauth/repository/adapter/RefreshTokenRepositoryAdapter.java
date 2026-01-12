package io.codebuddy.closetbuddy.domain.Oauth.repository.adapter;

import io.codebuddy.closetbuddy.domain.Oauth.Entity.RefreshTokenBlackList ;
import io.codebuddy.closetbuddy.domain.Oauth.Entity.RefreshToken ;
import io.codebuddy.closetbuddy.domain.Oauth.repository.RefreshTokenBlackListRepository ;
import io.codebuddy.closetbuddy.domain.Oauth.repository.RefreshTokenRepository ;
import io.codebuddy.closetbuddy.domain.Oauth.repository.TokenRepository ;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//RefreshTokenRepository와 RefreshTokenBlackListRepository 이 두 저장소를 TokenRepository 형태로 변환해주는 역할을 하는 어댑터이다.
@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements TokenRepository {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    private final EntityManager entityManager;

    @Override
    public RefreshToken save(Member member, String token) {
        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .member(member)
                        .refreshToken(token)
                        .build()
        );
    }

    @Override
    public RefreshTokenBlackList addBlackList(RefreshToken refreshToken) {
        return refreshTokenBlackListRepository.save(
                RefreshTokenBlackList.builder()
                        .refreshToken(refreshToken)
                        .build()
        );
    }

    //회원 ID의 블랙리스트에 없는 첫 번째 RefreshToken 반환
    @Override
    public Optional<RefreshToken> findValidRefToken(Long memberId) {
        return entityManager.createQuery(
                        "select rf from RefreshToken rf left join RefreshTokenBlackList rtb on rtb.refreshToken = rf where rf.member.id = :memberId and rtb.id is null" //EntityManager로 JPQL 쿼리 생성 시작
                        , RefreshToken.class)
                .setParameter("memberId", memberId) //파라미터 바인딩(SQL 인젝션 방지)
                .getResultStream()
                .findFirst(); //첫번째 유효한 토큰만 Optional로 반환
    }

}