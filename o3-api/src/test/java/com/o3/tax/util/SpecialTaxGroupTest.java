package com.o3.tax.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class SpecialTaxGroupTest {

    @Test
    @DisplayName("보험료공제금액 계산")
    void success_whenCalculateInsurance() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);

        // when then
        Assertions.assertThat(SpecialTaxGroup.INSURANCE_DEDUCTION_AMOUNT.calculateTax(amount, BigDecimal.ZERO)).isEqualTo(amount.multiply(BigDecimal.valueOf(0.12)));
    }

    @Test
    @DisplayName("교육비공제금액 계산")
    void success_whenCalculateEducational() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);

        // when then
        Assertions.assertThat(SpecialTaxGroup.EDUCATIONAL_DEDUCTION_AMOUNT.calculateTax(amount, BigDecimal.ZERO)).isEqualTo(amount.multiply(BigDecimal.valueOf(0.15)));
    }

    @Test
    @DisplayName("기부금공제금액 계산")
    void success_whenCalculateDonation() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);

        // when then
        Assertions.assertThat(SpecialTaxGroup.DONATION_DEDUCTION_AMOUNT.calculateTax(amount, BigDecimal.ZERO)).isEqualTo(amount.multiply(BigDecimal.valueOf(0.15)));
    }

    @Test
    @DisplayName("의료비공제금액 계산")
    void success_whenCalculateMedical() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);
        BigDecimal total = BigDecimal.valueOf(200_000L);

        // when then
        Assertions.assertThat(SpecialTaxGroup.MEDICAL_DEDUCTION_AMOUNT.calculateTax(amount, total)).isEqualTo(amount.subtract(total.multiply(BigDecimal.valueOf(0.03))).multiply(BigDecimal.valueOf(0.15)));
    }

    @Test
    @DisplayName("의료비공제금액 계산 (마이너스일 경우)")
    void success_whenCalculateMedicalWithMinus() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100_000L);
        BigDecimal total = BigDecimal.valueOf(200_000_000L);

        // when then
        Assertions.assertThat(SpecialTaxGroup.MEDICAL_DEDUCTION_AMOUNT.calculateTax(amount, total)).isZero();
    }

}