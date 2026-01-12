package io.codebuddy.closetbuddy.account.model.entity;

import io.codebuddy.closetbuddy.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    private Long balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false, unique = true)
    private Member member;

    @Builder
    public Account(Long accountId){
        this.accountId=accountId;
        this.balance=0L;
    }

    public void charge(Long amount){
        if(amount<=0) throw new IllegalArgumentException("충전 금액은 0보다 커야합니다.");
        this.balance+=balance;
    }

    public void withdraw(Long amount){
        if(this.balance<amount) throw new IllegalArgumentException("잔액이 부족합니다.");
        this.balance-=balance;
    }

}
