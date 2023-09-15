package com.o3.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Salary {

    @JsonProperty("이름")
    private final String name;

    @JsonProperty("주민등록번호")
    private final String regNo;

    @JsonProperty("총지급액")
    private final String totalPaymentAmount;
}
