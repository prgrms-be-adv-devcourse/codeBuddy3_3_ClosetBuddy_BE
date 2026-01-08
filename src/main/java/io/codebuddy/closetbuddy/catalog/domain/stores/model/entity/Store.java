package io.codebuddy.closetbuddy.catalog.domain.stores.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;
    private Long sellerId;
    private String storeName;

    @Builder
    public Store(Long storeId, Long sellerId, String storeName) {
        this.storeId = storeId;
        this.sellerId = sellerId;
        this.storeName = storeName;
    }

    public void update(Long storeId, Long sellerId, String storerName) {
        this.storeId = storeId;
        this.sellerId = sellerId;
        this.storeName = storerName;
    }
}
