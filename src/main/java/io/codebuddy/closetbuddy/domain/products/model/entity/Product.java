package io.codebuddy.closetbuddy.domain.products.model.entity;

import io.codebuddy.closetbuddy.domain.products.model.dto.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    //PK는 productId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name", length = 200)
    private String productName;
    @Column(name = "product_price", nullable = false)
    private Long productPrice;
    @Column(name = "product_stock", nullable = false)
    private int  productStock;
    @Column(name = "store_id", nullable = false)
    private Long storeId;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    public Product(Long productId, String productName, Long productPrice, int productStock, Long storeId, String imageUrl, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.storeId = storeId;
        this.imageUrl = imageUrl;
        //카테고리는 분류가 안되어 있을 수 있음(null 허용)
        this.category = category;
    }

    //update 비지니스 로직을 담은 메서드
    public void update(String productName, Long productPrice, int productStock, Long storeId, String imageUrl, Category category) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.storeId = storeId;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
