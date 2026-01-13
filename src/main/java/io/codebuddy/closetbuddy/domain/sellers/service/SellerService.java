package io.codebuddy.closetbuddy.domain.sellers.service;

import io.codebuddy.closetbuddy.domain.sellers.model.dto.UpsertSellerRequest;
import io.codebuddy.closetbuddy.domain.sellers.model.entity.Seller;
import io.codebuddy.closetbuddy.domain.sellers.repository.SellerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerJpaRepository sellerJpaRepository;

    //판매자 등록 서비스 로직
    @Transactional
    public Seller save(UpsertSellerRequest request) {

        Seller seller = Seller.builder()
                .sellerId(request.sellerId())
                .member(request.member())
                .sellerName(request.sellerName())
                .build();

        return sellerJpaRepository.save(seller);
    }
}
