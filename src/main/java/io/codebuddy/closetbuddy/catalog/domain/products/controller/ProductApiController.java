package io.codebuddy.closetbuddy.catalog.domain.products.controller;

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
@RequestMapping("/catalog/product")
public class ProductApiController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> create(@RequestBody UpsertProductRequest request) {
        Product saved = productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
}
