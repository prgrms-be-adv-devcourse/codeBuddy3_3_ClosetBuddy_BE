package io.codebuddy.closetbuddy.catalog.domain.sellers.controller;


import io.codebuddy.closetbuddy.catalog.domain.sellers.model.dto.UpsertSellerRequest;
import io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity.Seller;
import io.codebuddy.closetbuddy.catalog.domain.sellers.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog/sellers")
public class SellerApiController {

    private final SellerService sellerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Seller> create(
            @RequestBody UpsertSellerRequest request
            ) {
        Seller saved = sellerService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


}
