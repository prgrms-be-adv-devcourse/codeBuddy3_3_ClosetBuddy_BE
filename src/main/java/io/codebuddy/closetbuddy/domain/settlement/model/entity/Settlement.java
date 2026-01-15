package io.codebuddy.closetbuddy.domain.settlement.model.entity;

import io.codebuddy.closetbuddy.domain.sellers.model.entity.Seller;
import io.codebuddy.closetbuddy.domain.stores.model.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement {

    @Id
    @Column(name = "settlement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_di", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @Column(name = "settlement_month", nullable = false)
    private LocalDate settlementMonth;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(name = "settled_at", nullable = false)
    private LocalDateTime settledAt;

    @Column(name = "settlement_status")

    private Settlement(Store store, Seller seller, LocalDate settlementMonth, Long totalAmount, LocalDateTime settledAt) {
        this.store = store;
        this.seller = seller;
        this.settlementMonth = settlementMonth;
        this.totalAmount = totalAmount;
        this.settledAt = settledAt;
    }
}
