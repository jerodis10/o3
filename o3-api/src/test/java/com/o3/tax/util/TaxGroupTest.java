package com.o3.tax.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaxGroupTest {

    @Test
    @DisplayName("근로소득세액공제금액 계산")
    void success_whenCalculateEarnIncome() {
        // given
        long amount = 100_000L;

        // when then
        Assertions.assertThat(TaxGroup.EARNEDINCOME_TAX_CREDIT_AMOUNT.calculateTax(amount,0L,0L,0L,0L)).isEqualTo((long) (amount * 0.55));
    }

    @Test
    @DisplayName("퇴직연금세액공제금액 계산")
    void success_whenCalculateRetirement() {
        // given
        long amount = 100_000L;

        // when then
        Assertions.assertThat(TaxGroup.RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT.calculateTax(amount,0L,0L,0L,0L)).isEqualTo((long) (amount * 0.15));
    }

    @Test
    @DisplayName("표준세액공제금액 계산")
    void success_whenCalculateStandard() {
        // given
        long amount = 100_000L;

        // when then
        Assertions.assertThat(TaxGroup.STANDARD_TAX_DEDUCTION_AMOUNT.calculateTax(amount,0L,0L,0L,0L)).isEqualTo(130_000L);
    }

    @Test
    @DisplayName("표준세액공제금액 계산 (기준금액 이상인 경우)")
    void success_whenCalculateStandardWithZero() {
        // given
        long amount = 200_000L;

        // when then
        Assertions.assertThat(TaxGroup.STANDARD_TAX_DEDUCTION_AMOUNT.calculateTax(amount,0L,0L,0L,0L)).isZero();
    }

    @Test
    @DisplayName("특별세액공제금액 계산")
    void success_whenCalculateSpecial() {
        // given
        long insuranceDeductionAmount = 100_000L;
        long educationalDeductionAmount = 100_000L;
        long donationDeductionAmount = 100_000L;
        long medicalDeductionAmount = 100_000L;

        // when then
        Assertions.assertThat(TaxGroup.SPECIAL_TAX_DEDUCTION_AMOUNT.calculateTax(
                insuranceDeductionAmount,
                educationalDeductionAmount,
                donationDeductionAmount,
                medicalDeductionAmount,
                0L)).isEqualTo(insuranceDeductionAmount + educationalDeductionAmount + donationDeductionAmount + medicalDeductionAmount);
    }

    @Test
    @DisplayName("결정세액 계산")
    void success_whenCalculateDetermined() {
        // given
        long taxAmount = 100_000_000L;
        long retirementPensionTaxDeductionAmount = 100_000L;
        long earnedIncomeTaxCreditAmount = 100_000L;
        long specialTaxDeductionAmount = 100_000L;
        long standardTaxDeductionAmount = 100_000L;

        // when then
        Assertions.assertThat(TaxGroup.DETERMINED_TAX_AMOUNT.calculateTax(
                taxAmount,
                retirementPensionTaxDeductionAmount,
                earnedIncomeTaxCreditAmount,
                specialTaxDeductionAmount,
                standardTaxDeductionAmount)).isEqualTo(taxAmount - retirementPensionTaxDeductionAmount - earnedIncomeTaxCreditAmount - specialTaxDeductionAmount - standardTaxDeductionAmount);
    }

}