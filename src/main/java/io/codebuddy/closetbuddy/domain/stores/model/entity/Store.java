package io.codebuddy.closetbuddy.domain.stores.model.entity;

import io.codebuddy.closetbuddy.domain.sellers.model.entity.Seller;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "store")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;
    @Column(name = "store_name", nullable = false, length = 100)
    private String storeName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller sellerId;

    @Builder
    public Store(Long storeId, Seller sellerId, String storeName) {
        this.storeId = storeId;
        this.sellerId = sellerId;
        this.storeName = storeName;
    }

    public void update(String storerName) {
        this.storeName = storerName;
    }
}
