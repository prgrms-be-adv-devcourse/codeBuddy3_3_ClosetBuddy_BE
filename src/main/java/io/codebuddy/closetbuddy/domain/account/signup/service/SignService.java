package io.codebuddy.closetbuddy.domain.account.signup.service;

import io.codebuddy.closetbuddy.domain.account.common.model.entity.LoginMember;
import io.codebuddy.closetbuddy.domain.account.common.repository.LoginRepository;
import io.codebuddy.closetbuddy.domain.account.common.model.dto.UserReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignService {
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginMember create(UserReqDTO userReqDTO) {

        LoginMember loginmember = LoginMember.builder()
                .username(userReqDTO.getUsername())
                .userid(userReqDTO.getUserid())
                .email(userReqDTO.getEmail())
                .password(passwordEncoder.encode(userReqDTO.getPassword()))
                .address(userReqDTO.getAddress())
                .phone(userReqDTO.getPhone())
                .build();


        return loginRepository.save(loginmember);
    }

}
