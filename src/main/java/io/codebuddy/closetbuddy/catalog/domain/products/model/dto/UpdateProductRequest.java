package io.codebuddy.closetbuddy.catalog.domain.products.model.dto;

public record UpdateProductRequest(
        String productName,
        Long productPrice,
        int productStock
        //추후 상품 설명, 판매상태, 이미지 등 추가 예정
) {
}
