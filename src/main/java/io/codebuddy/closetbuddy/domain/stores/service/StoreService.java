package io.codebuddy.closetbuddy.domain.stores.service;


import io.codebuddy.closetbuddy.domain.products.repository.ProductJpaRepository;
import io.codebuddy.closetbuddy.domain.stores.model.dto.StoreResponse;
import io.codebuddy.closetbuddy.domain.stores.model.dto.UpdateStoreRequest;
import io.codebuddy.closetbuddy.domain.stores.model.dto.UpsertStoreRequest;
import io.codebuddy.closetbuddy.domain.stores.model.entity.Store;
import io.codebuddy.closetbuddy.domain.stores.repository.StoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreJpaRepository storeJpaRepository;
    //상점에서 상품 제어를 위한 JpaRepository
    private final ProductJpaRepository productJpaRepository;

    //상점 등록 서비스 로직
    public Store save(UpsertStoreRequest request) {

        Store store = Store.builder()
                .storeId(request.storeId())
                .sellerId(request.sellerId())
                .storeName(request.storeName())
                .build();

        return storeJpaRepository.save(store);
    }

    //내 가게 정보 조회
    public StoreResponse getStore(Long storeId) {
        Store store = storeJpaRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다."));
        return StoreResponse.from(store);
    }

    @Transactional
    //내 가게 정보 수정
    public StoreResponse updateStore(Long storeId, UpdateStoreRequest request) {
        Store store = storeJpaRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException(storeId + "의 상점을 찾을 수 없습니다."));
        store.update(
                request.storeName()
        );
        return StoreResponse.from(store);
    }

    //내 가게 삭제
    @Transactional
    public void deleteStore(Long storeId) {
        if(!storeJpaRepository.existsById(storeId)) {
            throw new IllegalArgumentException(storeId + "의 상점을 찾을 수 없습니다.");
        }
        storeJpaRepository.deleteById(storeId);
    }

}
