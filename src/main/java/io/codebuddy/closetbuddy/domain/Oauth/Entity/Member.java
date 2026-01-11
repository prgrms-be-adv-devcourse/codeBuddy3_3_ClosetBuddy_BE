package io.codebuddy.closetbuddy.domain.Oauth.Entity;

import io.codebuddy.closetbuddy.domain.Oauth.dto.Role ;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="member_name")
    private String name;

    @Column(name="member_email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    private LocalDateTime signedAt = LocalDateTime.now();

    @Builder
    public Member(String member_email, String member_name) {
        this.name = member_name;
        this.email = member_email;

    }
}
