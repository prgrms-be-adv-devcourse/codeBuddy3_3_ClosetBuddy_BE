package io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "seller",
    uniqueConstraints = @UniqueConstraint(name = "uk_seller_member", columnNames = "id"))
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long sellerId;
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "seller_name")
    private String sellerName;

    @Builder
    public Seller(Long sellerId, Long id, String sellerName) {
        this.sellerId = sellerId;
        this.id = id;
        this.sellerName = sellerName;
    }

    //update 메서드 로직
    public void update(Long sellerId, Long id, String sellerName) {
        this.sellerId = sellerId;
        this.id = id;
        this.sellerName = sellerName;
    }
}
