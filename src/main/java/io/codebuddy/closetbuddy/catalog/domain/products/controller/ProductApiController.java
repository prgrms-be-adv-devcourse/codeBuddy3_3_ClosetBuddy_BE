package io.codebuddy.closetbuddy.catalog.domain.products.controller;

import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.Category;
import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.ProductResponse;
import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.UpdateProductRequest;
import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.UpsertProductRequest;
import io.codebuddy.closetbuddy.catalog.domain.products.model.entity.Product;
import io.codebuddy.closetbuddy.catalog.domain.products.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class ProductApiController {

    private final ProductService productService;

    @Operation(
            summary = "상품 등록",
            description = "판매자의 상품 정보를 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "상품 등록 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "중복된 상품 데이터"
            )
    })
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> create(
            //RequestParam은 member 기능 개발 완료 시 @AuthenticationPrincipal로 교체하여 사용
            @RequestParam Long memberId,
            @RequestBody UpsertProductRequest request
    ) {
        Product saved = productService.save(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "상품 단건 조회",
            description = "하나의 상품을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 단건 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 데이터 없음"
            )
    })
    @GetMapping("products/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId
    ) {
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "상품 수정",
            description = "상품을 수정합니다."
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
                    responseCode = "404",
                    description = "수정할 상품 없음"
            )
    })
    @PutMapping("products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        ProductResponse response = productService.updateProduct(productId, request);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "상품 삭제",
            description = "상품을 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "상품 삭제 성공(반환 데이터 없음)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "삭제할 상품 없음"
            )
    })
    @DeleteMapping("products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "전체 상품 조회",
            description = "전체 상품을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 데이터 없음"
            )
    })
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            summary = "특정 가게 상품 조회",
            description = "특정 가게의 상품을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 데이터 없음"
            )
    })
    @GetMapping("stores/{storeId}/products")
    public ResponseEntity<List<ProductResponse>> getProductByStoreId(
            @PathVariable Long storeId
    ) {
        List<ProductResponse> products = productService.getProductByStoreId(storeId);
        return ResponseEntity.ok(products);
    }
}
