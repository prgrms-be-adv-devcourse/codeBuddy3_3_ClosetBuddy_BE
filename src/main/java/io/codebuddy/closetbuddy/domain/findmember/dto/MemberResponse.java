package io.codebuddy.closetbuddy.domain.findmember.dto;

public record MemberResponse(Long id, String userid, String name, String email, String address, String phone, String role) {
}
