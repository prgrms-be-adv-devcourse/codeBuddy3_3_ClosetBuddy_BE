package io.codebuddy.closetbuddy.catalog.domain.products.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product", schema = "catalog_db")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    //PK는 productId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;
    @Column(name = "product_price", nullable = false)
    private Long productPrice;
    @Column(name = "product_stock", nullable = false)
    private int  productStock;
    @Column(name = "store_id", nullable = false)
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
