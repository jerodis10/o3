package com.o3.tax.dto.request;

import com.o3.member.domain.Member;
import com.o3.security.common.AESUtil;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class TaxScrapRequest {

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, max = 6, message = "이름은 2글자 이상, 6글자 이하로 입력해주세요.")
    private final String name;

    @NotBlank(message = "주민번호를 입력해주세요")
    @Size(min = 14, max = 14, message = " 주민등록번호는 -을 포함하여 14글자를 입력해주세요.")
    private final String regNo;


    public static TaxScrapRequest of(Member member) {
        return TaxScrapRequest.builder()
                .name(member.getName())
                .regNo(AESUtil.decrypt(member.getRegNo()))
                .build();
    }

}
