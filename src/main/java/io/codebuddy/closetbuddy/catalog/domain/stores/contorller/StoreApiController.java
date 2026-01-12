package io.codebuddy.closetbuddy.catalog.domain.stores.contorller;


import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.StoreResponse;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.UpdateStoreRequest;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.UpsertStoreRequest;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.entity.Store;
import io.codebuddy.closetbuddy.catalog.domain.stores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class StoreApiController {

    private final StoreService storeService;

    //상점 등록
    @PostMapping("/stores")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Store> create(
            @RequestBody UpsertStoreRequest request
            ) {
        Store saved = storeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    //내 가게 조회
    @GetMapping("/stores/{store_id}")
    public ResponseEntity<StoreResponse> getStore(
            @PathVariable Long store_id
    ) {
        StoreResponse response = storeService.getStore(store_id);
        return ResponseEntity.ok(response);
    }

    //내 가게 정보 수정
    @PutMapping("stores/{store_id}")
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable Long store_id,
            @RequestBody UpdateStoreRequest request
            ) {
        StoreResponse response = storeService.updateStore(store_id, request);
        return ResponseEntity.ok(response);
    }

    //내 가게 삭제(폐점)
    @DeleteMapping("stores/{store_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStore(@PathVariable Long store_id) {
        storeService.deleteStore(store_id);
        return ResponseEntity.noContent().build();
    }
}
