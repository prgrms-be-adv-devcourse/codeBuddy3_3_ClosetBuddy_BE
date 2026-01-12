package io.codebuddy.closetbuddy.catalog.domain.sellers.controller;


import io.codebuddy.closetbuddy.catalog.domain.sellers.model.dto.UpsertSellerRequest;
import io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity.Seller;
import io.codebuddy.closetbuddy.catalog.domain.sellers.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog/sellers")
public class SellerApiController {

    private final SellerService sellerService;

    @Operation(
            summary = "판매자 등록",
            description = "판매자를 등록합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "판매자 등록 완료"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "중복된 판매자 데이터"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Seller> create(
            @RequestBody UpsertSellerRequest request
            ) {
        Seller saved = sellerService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


}
