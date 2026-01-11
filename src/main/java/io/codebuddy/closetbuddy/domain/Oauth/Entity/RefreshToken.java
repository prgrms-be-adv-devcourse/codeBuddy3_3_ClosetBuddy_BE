package io.codebuddy.closetbuddy.domain.Oauth.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "Refresh_Table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //PK 로 자동 증가하는 고유번호. -> refresh_token_id 컬럼에 매핑한다.

    private String refreshToken; //실제 Refresh Token 문자열

    //소유자 회원. 위의 refreshToken을 발급받은 Member 객체. memeber_id로 join
    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private final LocalDateTime createdAt = LocalDateTime.now(); //발급 시각. 토큰이 언제 만들었는지 기록

    @Builder
    public RefreshToken(String refreshToken, Member member) {
        this.refreshToken = refreshToken;
        this.member = member;
    }
}
