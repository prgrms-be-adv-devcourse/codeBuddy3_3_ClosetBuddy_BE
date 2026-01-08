package io.codebuddy.closetbuddy.catalog.domain.products.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    //PK는 productId
    @Id
    private Long productId;
    private String productName;
    private Long productPrice;
    private int  productStock;
    private Long storeId;

    @Builder
    public Product(Long productId, String productName, Long productPrice, int productStock, Long storeId) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.storeId = storeId;
    }

}
