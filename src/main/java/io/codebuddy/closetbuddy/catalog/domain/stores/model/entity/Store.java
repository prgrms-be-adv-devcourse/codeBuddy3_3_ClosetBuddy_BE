package io.codebuddy.closetbuddy.catalog.domain.stores.model.entity;

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
    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Builder
    public Store(Long storeId, Long sellerId, String storeName) {
        this.storeId = storeId;
        this.sellerId = sellerId;
        this.storeName = storeName;
    }

    public void update(String storerName) {
        this.storeName = storerName;
    }
}
