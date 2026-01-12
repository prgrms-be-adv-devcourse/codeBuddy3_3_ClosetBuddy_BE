package io.codebuddy.closetbuddy.domain.account.signup.dto;

import io.codebuddy.closetbuddy.domain.common.model.dto.Role;
import lombok.Getter;

@Getter
public class UserReqDTO {

    private String username;
    private String userid;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Role role;

}
