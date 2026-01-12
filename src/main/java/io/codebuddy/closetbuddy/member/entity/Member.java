package io.codebuddy.closetbuddy.member.entity;


import io.codebuddy.closetbuddy.account.model.entity.Account;
import io.codebuddy.closetbuddy.member.role.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member") // 테이블명 명시
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_name", length = 50, nullable = false, unique = true)
    private String memberName;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "password")
    private String password;

    @Column(name = "member_email", unique = true)
    private String memberEmail;

    @Column(name = "member_address")
    private String memberAddress;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    // 양방향 관계 설정 (선택 사항이지만 추천)
    // mappedBy = "member"는 Account 엔티티의 member 필드명을 의미함
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Account account;

    @Builder
    public Member(String memberName, String memberId, String password, String memberEmail, String memberAddress, String phoneNumber, Role role) {
        this.memberName = memberName;
        this.memberId = memberId;
        this.password = password;
        this.memberEmail = memberEmail;
        this.memberAddress = memberAddress;
        this.phoneNumber = phoneNumber;
        this.role = (role != null) ? role : Role.MEMBER; // Default 처리
    }
}
