package com.o3.tax.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.o3.tax.util.NumberUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@RequiredArgsConstructor
public class TaxRefundResponse {

    @JsonProperty("이름")
    private final String name;

    @JsonProperty("결정세액")
    private final String determinedTaxAmount;

    @JsonProperty("퇴직연금세액공제")
    private final String retirementPensionTaxCredit;

    public static TaxRefundResponse of(String name, BigDecimal retirementPensionTaxDeductionAmount, BigDecimal determinedTaxAmount) {
        return TaxRefundResponse.builder()
                .name(name)
                .retirementPensionTaxCredit(NumberUtil.numberFormatter(retirementPensionTaxDeductionAmount))
                .determinedTaxAmount(NumberUtil.numberFormatter(determinedTaxAmount))
                .build();
    }
}
