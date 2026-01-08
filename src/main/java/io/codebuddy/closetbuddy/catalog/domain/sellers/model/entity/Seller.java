package io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;
    private Long memberId;

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
