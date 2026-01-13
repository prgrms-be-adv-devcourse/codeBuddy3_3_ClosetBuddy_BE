package io.codebuddy.closetbuddy.domain.products.service;

import io.codebuddy.closetbuddy.domain.products.model.dto.ProductResponse;
import io.codebuddy.closetbuddy.domain.products.model.dto.UpdateProductRequest;
import io.codebuddy.closetbuddy.domain.products.model.dto.UpsertProductRequest;
import io.codebuddy.closetbuddy.domain.products.model.entity.Product;
import io.codebuddy.closetbuddy.domain.products.repository.ProductJpaRepository;

import io.codebuddy.closetbuddy.domain.sellers.model.entity.Seller;
import io.codebuddy.closetbuddy.domain.sellers.repository.SellerJpaRepository;
import io.codebuddy.closetbuddy.domain.sellers.service.SellerService;
import io.codebuddy.closetbuddy.domain.stores.model.entity.Store;
import io.codebuddy.closetbuddy.domain.stores.repository.StoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductJpaRepository productJpaRepository;
    private final StoreJpaRepository storeJpaRepository;
    private final SellerJpaRepository sellerJpaRepository;
    private final SellerService sellerService;

    //상품 등록
    @Transactional
    public Product save(UpsertProductRequest request, Long memberId) {
        // 가게 조회
        Store store = storeJpaRepository.findById(request.storeId())
                .orElseThrow(() -> new IllegalArgumentException("Store not found"));

        //판매자 조회 (id -> seller_id)
        Seller seller = sellerJpaRepository.findByMemberId(memberId)
                .orElseThrow( () -> new IllegalArgumentException("This member is not seller."));

        //주인 확인
        if (!store.getSellerId().equals(seller.getSellerId())) {
            throw new IllegalArgumentException("This store is not your store.");
        }

        Product product = Product.builder()
                .productId(request.productId())
                .productName(request.productName())
                .productPrice(request.productPrice())
                .productStock(request.productStock())
                .storeId(request.storeId())
                .imageUrl(request.imageUrl())
                .category(request.category())
                .build();

        return productJpaRepository.save(product);
    }

    //상품 상세조회(단건)
    @Transactional
    public ProductResponse getProduct(Long productId) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow( () -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return ProductResponse.from(product);
    }

    //특정 가게의 상품 목록 조회
    @Transactional
    public List<ProductResponse> getProductByStoreId(Long storeId) {
        return productJpaRepository.findByStoreId(storeId).stream()
                .map(ProductResponse::from)
                .toList();
    }

    //전체 상품목록 조회
    @Transactional
    public List<ProductResponse> getAllProducts() {
        return productJpaRepository.findAll().stream()
                .map(ProductResponse::from)
                .toList();
    }

    //상품 수정
    @Transactional
    public ProductResponse updateProduct(Long productId, UpdateProductRequest request) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID : " + productId));

        product.update(request.productName(),  request.productPrice(), request.productStock(), request.storeId(), request.imageUrl(),request.category());
        return ProductResponse.from(product);
    }

    //상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        if(!productJpaRepository.existsById(productId)) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다. ID : " + productId);
        }
        productJpaRepository.deleteById(productId);
    }

}
