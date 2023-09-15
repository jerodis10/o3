package com.o3.member.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class IncomeDeduction {

    @JsonAlias({"금액","총납임금액"})
    private final String amount;

    @JsonProperty("소득구분")
    private final String incomeClassification;
}
