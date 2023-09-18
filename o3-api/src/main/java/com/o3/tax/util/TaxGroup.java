package com.o3.tax.util;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public enum TaxGroup {

    DETERMINED_TAX_AMOUNT("결정세액", (taxAmount, sumAmount) -> {
        BigDecimal result = taxAmount.subtract(sumAmount);
        if(result.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;
        return result;
    }),
    EARNEDINCOME_TAX_CREDIT_AMOUNT("근로소득세액공제금액", (amount, amount2) -> amount.multiply(new BigDecimal("0.55"))),
    SPECIAL_TAX_DEDUCTION_AMOUNT("특별세액공제금액", (amount, amount2) -> amount),
    RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT("퇴직연금세액공제금액", (amount, amount2) -> amount.multiply(new BigDecimal("0.15"))),
    STANDARD_TAX_DEDUCTION_AMOUNT("표준세액공제금액", (amount, amount2) -> {
        if(amount.compareTo(new BigDecimal("130000")) < 0) return new BigDecimal("130000");
        return BigDecimal.ZERO;
    });

    private final String taxName;

    private final BinaryOperator<BigDecimal> expression;

    TaxGroup(String taxName, BinaryOperator<BigDecimal> expression) {
        this.taxName = taxName;
        this.expression = expression;
    }

    public BigDecimal calculateTax(BigDecimal amount, BigDecimal amount2) {
        return expression.apply(amount, amount2);
    }

}
