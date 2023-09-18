package com.o3.tax.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.o3.tax.util.TaxGroup.DETERMINED_TAX_AMOUNT;

class TaxGroupTest {

    @Test
    @DisplayName("근로소득세액공제금액 계산")
    void success_whenCalculateEarnIncome() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);

        // when then
        Assertions.assertThat(TaxGroup.EARNEDINCOME_TAX_CREDIT_AMOUNT.calculateTax(amount,BigDecimal.ZERO)).isEqualTo(amount.multiply(new BigDecimal("0.55")));
    }

    @Test
    @DisplayName("퇴직연금세액공제금액 계산")
    void success_whenCalculateRetirement() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);

        // when then
        Assertions.assertThat(TaxGroup.RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT.calculateTax(amount,BigDecimal.ZERO)).isEqualTo(amount.multiply(new BigDecimal("0.15")));
    }

    @Test
    @DisplayName("표준세액공제금액 계산")
    void success_whenCalculateStandard() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);

        // when then
        Assertions.assertThat(TaxGroup.STANDARD_TAX_DEDUCTION_AMOUNT.calculateTax(amount,BigDecimal.ZERO)).isEqualTo(BigDecimal.valueOf(130_000L));
    }

    @Test
    @DisplayName("표준세액공제금액 계산 (기준금액 이상인 경우)")
    void success_whenCalculateStandardWithZero() {
        // given
        BigDecimal amount = BigDecimal.valueOf(200_000L);

        // when then
        Assertions.assertThat(TaxGroup.STANDARD_TAX_DEDUCTION_AMOUNT.calculateTax(amount,BigDecimal.ZERO)).isZero();
    }

    @Test
    @DisplayName("결정세액 계산")
    void success_whenCalculateDetermined() {
        // given
        BigDecimal taxAmount = BigDecimal.valueOf(100_000_000L);
        BigDecimal sumAmount = BigDecimal.valueOf(100_000L);

        // when then
        Assertions.assertThat(DETERMINED_TAX_AMOUNT.calculateTax(taxAmount, sumAmount)).isEqualTo(taxAmount.subtract(sumAmount));
    }

}