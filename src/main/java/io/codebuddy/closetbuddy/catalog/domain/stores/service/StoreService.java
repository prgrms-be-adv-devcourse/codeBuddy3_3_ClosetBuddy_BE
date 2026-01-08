package io.codebuddy.closetbuddy.catalog.domain.stores.service;


import io.codebuddy.closetbuddy.catalog.domain.sellers.model.dto.UpsertSellerRequest;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.UpsertStoreRequest;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.entity.Store;
import io.codebuddy.closetbuddy.catalog.domain.stores.repository.StoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreJpaRepository storeJpaRepository;

    public Store save(UpsertStoreRequest request) {

        Store store = Store.builder()
                .storeId(request.storeId())
                .sellerId(request.sellerId())
                .storeName(request.storeName())
                .build();

        return storeJpaRepository.save(store);
    }
}
