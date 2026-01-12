package io.codebuddy.closetbuddy.catalog.domain.stores.contorller;


import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.StoreResponse;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.UpdateStoreRequest;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.dto.UpsertStoreRequest;
import io.codebuddy.closetbuddy.catalog.domain.stores.model.entity.Store;
import io.codebuddy.closetbuddy.catalog.domain.stores.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class StoreApiController {

    private final StoreService storeService;

    @Operation(
            summary = "가게 등록",
            description = "가게를 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "가게 생성 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "중복된 가게 데이터"
            )
    })
    @PostMapping("/stores")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Store> create(
            @RequestBody UpsertStoreRequest request
            ) {
        Store saved = storeService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "가게 조회",
            description = "가게를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "가게 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "가게 데이터 없음"
            )
    })
    @GetMapping("/stores/{store_id}")
    public ResponseEntity<StoreResponse> getStore(
            @PathVariable Long store_id
    ) {
        StoreResponse response = storeService.getStore(store_id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "가게 수정",
            description = "가게 정보를 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "가게 수정 성공(반환 데이터 없음)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "가게 데이터 없음"
            )

    })
    @PutMapping("stores/{store_id}")
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable Long store_id,
            @RequestBody UpdateStoreRequest request
            ) {
        StoreResponse response = storeService.updateStore(store_id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "가게 삭제",
            description = "가게를 삭제(폐점)합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "가게 삭제 성공(반환 값 없음)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "가게 데이터 없음"
            )
    })
    @DeleteMapping("stores/{store_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStore(@PathVariable Long store_id) {
        storeService.deleteStore(store_id);
        return ResponseEntity.noContent().build();
    }
}
