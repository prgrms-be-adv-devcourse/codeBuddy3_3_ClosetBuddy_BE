package io.codebuddy.closetbuddy.domain.sellers.service;

import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.common.repository.MemberRepository;
import io.codebuddy.closetbuddy.domain.sellers.model.dto.SellerResponse;
import io.codebuddy.closetbuddy.domain.sellers.model.dto.SellerUpsertRequest;
import io.codebuddy.closetbuddy.domain.sellers.model.entity.Seller;
import io.codebuddy.closetbuddy.domain.sellers.repository.SellerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerJpaRepository sellerJpaRepository;
    private final MemberRepository memberRepository;

    //판매자 등록 서비스 로직
    @Transactional
    public Long registerSeller(Long loginMemberId, SellerUpsertRequest sellerUpsertRequest) {

        //검증 1: 이미 판매자로 등록된 회원인지 조회
        if(sellerJpaRepository.existsByMemberId(loginMemberId)) {
            throw new IllegalStateException("이미 판매자로 등록된 회원입니다.");
        }

        //검증 2: 실제로 존재하는 회원인지 조회
        Member member = memberRepository.findById(loginMemberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        //등록 : Member 정보를 넣어 Seller 생성
        Seller seller = Seller.builder()
                .member(member)
                .sellerName(sellerUpsertRequest.sellerName())
                .build();

        return sellerJpaRepository.save(seller).getSellerId();
    }

    //판매자 정보 조회 (Read)
    @Transactional(readOnly = true)
    public SellerResponse getSellerInfo(Long loginMemberId) {
        Seller seller = sellerJpaRepository.findByMemberId(loginMemberId)
                .orElseThrow(() -> new IllegalArgumentException("판매자가 아닌 사용자 입니다. 판매자 등록 후 이용해주세요."));

        return SellerResponse.from(seller);
    }

    //판매자 정보 수정(Update)
    public  void updateSeller(Long loginMemberId, SellerUpsertRequest request) {
        //판매자 정보를 우선 불러오기
        Seller seller = sellerJpaRepository.findByMemberId(loginMemberId)
                .orElseThrow(() -> new IllegalArgumentException("판매자가 아닌 사용자 입니다. 판매자 등록 후 이용해주세요."));

        seller.update(request.sellerName());
    }

    //판매자 삭제(판매자 등록 해제, Delete)
    public void unregisterSeller(Long loginMemberId) {
        Seller seller = sellerJpaRepository.findByMemberId(loginMemberId)
                .orElseThrow( () -> new IllegalArgumentException("판매자가 아닌 사용자 입니다."));

        //판매자 등록 해제 이후 이어진 store, product의 삭제 로직 구현 필요
        sellerJpaRepository.delete(seller);
    }
}
