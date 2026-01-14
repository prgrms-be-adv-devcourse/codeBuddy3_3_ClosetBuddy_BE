package io.codebuddy.closetbuddy.domain.account.model.entity;

import io.codebuddy.closetbuddy.domain.account.model.vo.AccountStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "account_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_history_id")
    private Long accountHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "payment_key")
    private String paymentKey;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "account_amount")
    private Long accountAmount;

    @Column(name = "accounted_at")
    private LocalDateTime accountedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus=AccountStatus.READY;

    @Builder
    public AccountHistory(Long accountHistoryId, Account account, String paymentKey, String orderId, Long accountAmount, LocalDateTime accountedAt, AccountStatus accountStatus) {
        this.accountHistoryId = accountHistoryId;
        this.account=account;
        this.paymentKey = paymentKey;
        this.orderId = orderId;
        this.accountAmount = accountAmount;
        this.accountedAt = accountedAt;
        this.accountStatus = accountStatus;
    }

    public void cancel() {
        this.accountStatus = AccountStatus.CANCELED;
    }

}

