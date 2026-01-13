package io.codebuddy.closetbuddy.domain.account.signup.service;

import io.codebuddy.closetbuddy.domain.common.model.dto.Role;
import io.codebuddy.closetbuddy.domain.common.model.entity.Member;
import io.codebuddy.closetbuddy.domain.account.repository.LoginRepository;
import io.codebuddy.closetbuddy.domain.common.model.dto.UserReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignService {
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;


    public Member create(UserReqDTO userReqDTO) {

        Member loginmember = Member.builder()
                .username(userReqDTO.getUsername())
                .userid(userReqDTO.getUserid())
                .email(userReqDTO.getEmail())
                .password(passwordEncoder.encode(userReqDTO.getPassword()))
                .address(userReqDTO.getAddress())
                .phone(userReqDTO.getPhone())
                .role(Role.member)
                .build();


        return loginRepository.save(loginmember);
    }

}
