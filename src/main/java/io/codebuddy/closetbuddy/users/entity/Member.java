package io.codebuddy.closetbuddy.users.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="member_name", nullable = false) //nullable = false : 데이터베이스의 해당 컬럼 값이 null이 아니어야한다는 조건
    private String username;

    @Column(name = "member_id", nullable = false, unique = true)
    private String userid;

    @Column(name="member_email")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name="member_address")
    private String address;

    @Column(name="phone_number")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String userid, String email, String password, String address, String phone) {
        this.username = username;
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }
}