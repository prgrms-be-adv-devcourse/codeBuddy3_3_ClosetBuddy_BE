package io.codebuddy.closetbuddy.catalog.domain.sellers.service;

import io.codebuddy.closetbuddy.catalog.domain.sellers.model.dto.UpsertSellerRequest;
import io.codebuddy.closetbuddy.catalog.domain.sellers.model.entity.Seller;
import io.codebuddy.closetbuddy.catalog.domain.sellers.repository.SellerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerJpaRepository sellerJpaRepository;

    public Seller save(UpsertSellerRequest request) {

        Seller seller = Seller.builder()
                .sellerId(request.sellerId())
                .memberId(request.memberId())
                .build();

        return sellerJpaRepository.save(seller);
    }
}
