package com.o3.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.o3.member.domain.Member;
import com.o3.security.common.AESUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class MemberResponse {

    @JsonProperty("아이디")
    private final String loginId;

    @JsonProperty("이름")
    private final String name;

    @JsonProperty("주민등록번호")
    private final String regNo;

    @JsonProperty("권한")
    private final String role;

    public static MemberResponse toDto(Member member) {
        return MemberResponse.builder()
                .loginId(member.getLoginId())
                .name(member.getName())
                .regNo(AESUtil.decrypt(member.getRegNo()))
                .role(member.getRole())
                .build();
    }
}
