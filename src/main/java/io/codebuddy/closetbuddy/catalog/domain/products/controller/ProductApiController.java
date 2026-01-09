package io.codebuddy.closetbuddy.catalog.domain.products.controller;

import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.ProductResponse;
import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.UpdateProductRequest;
import io.codebuddy.closetbuddy.catalog.domain.products.model.dto.UpsertProductRequest;
import io.codebuddy.closetbuddy.catalog.domain.products.model.entity.Product;
import io.codebuddy.closetbuddy.catalog.domain.products.service.ProductService;
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

    //상품 등록
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> create(@RequestBody UpsertProductRequest request) {
        Product saved = productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    //상품 상세조회(단건)
    @GetMapping("products/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable Long productId
    ) {
        ProductResponse response = productService.getProduct(productId);
        return ResponseEntity.ok(response);
    }

    //상품 수정
    @PutMapping("products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request
    ) {
        ProductResponse response = productService.updateProduct(productId, request);
        return ResponseEntity.ok(response);
    }

    //상품 삭제
    @DeleteMapping("products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    //전체 상품 조회
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //특정 가게 상품조회
    @GetMapping("stores/{storeId}/products")
    public ResponseEntity<List<ProductResponse>> getProductByStoreId(
            @PathVariable Long storeId
    ) {
        List<ProductResponse> products = productService.getProductByStoreId(storeId);
        return ResponseEntity.ok(products);
    }
}
