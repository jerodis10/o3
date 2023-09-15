package com.o3.member.dto.request;

import com.o3.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MemberRequest {

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 12, message = "아이디는 4글자 이상, 12글자 이하로 입력해주세요.")
    private final String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8글자 이상, 20글자 이하로 입력해주세요.")
    private final String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, max = 6, message = "이름은 2글자 이상, 6글자 이하로 입력해주세요.")
    private final String name;

    private final String role = "MEMBER";

    @NotBlank(message = "주민번호를 입력해주세요")
    @Size(min = 14, max = 14, message = " 주민등록번호는 -을 포함하여 14글자를 입력해주세요.")
    public final String regNo;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .name(name)
                .password(password)
                .role(role)
                .regNo(regNo)
                .build();
    }

}
