package io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "seller",
    uniqueConstraints = @UniqueConstraint(name = "uk_seller_member", columnNames = "member_id"))
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long sellerId;
    @Column(name = "member_id", nullable = false)
    private Long memberId;
    @Column(name = "seller_name")
    private String sellerName;

    @Builder
    public Seller(Long sellerId, Long memberId) {
        this.sellerId = sellerId;
        this.memberId = memberId;
    }

    public void update(Long sellerId, Long memberId) {
        this.sellerId = sellerId;
        this.memberId = memberId;
    }
}
