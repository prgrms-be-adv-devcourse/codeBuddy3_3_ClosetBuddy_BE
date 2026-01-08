package io.codebuddy.closetbuddy.catalog.domain.stores.contorller;


import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.UpsertStoreRequest;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.entity.Store;
import io.codebuddy.closetbuddy.catalog.domain.stores.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog/stores")
public class StoreApiController {

    private final StoreService storeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Store> create(
            @RequestBody UpsertStoreRequest request
            ) {
        Store Store = storeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Store);
    }
}
