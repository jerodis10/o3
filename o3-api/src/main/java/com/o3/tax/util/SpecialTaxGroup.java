package com.o3.tax.util;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public enum SpecialTaxGroup {

    INSURANCE_DEDUCTION_AMOUNT("보험료공제금액", (amount, total) -> amount.multiply(new BigDecimal("0.12"))),
    EDUCATIONAL_DEDUCTION_AMOUNT("교육비공제금액", (amount, total) -> amount.multiply(new BigDecimal("0.15"))),
    DONATION_DEDUCTION_AMOUNT("기부금공제금액", (amount, total) -> amount.multiply(new BigDecimal("0.15"))),
    MEDICAL_DEDUCTION_AMOUNT("의료비공제금액", (amount, total) -> {
        BigDecimal result = amount.subtract(total.multiply(new BigDecimal("0.03"))).multiply(new BigDecimal("0.15"));
        if(result.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;
        return result;
    });

    private final String amountName;
    private final BinaryOperator<BigDecimal> expression;

    SpecialTaxGroup(String amountName, BinaryOperator<BigDecimal> expression) {
        this.amountName = amountName;
        this.expression = expression;
    }

    public BigDecimal calculateTax(BigDecimal amount, BigDecimal total) { return expression.apply(amount, total); }

}
