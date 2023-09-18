package com.o3.tax.util;

import com.o3.tax.domain.Tax;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TaxCalculatorTest {

    @Test
    @DisplayName("결정세액 계산")
    void success_whenCalculateTax() {
        // given
        BigDecimal retirementPension = BigDecimal.valueOf(6_000_000L);
        Tax tax = Tax.builder()
                .taxAmount(BigDecimal.valueOf(3_000_000L))
                .donation(BigDecimal.valueOf(150_000L))
                .educationExpenses(BigDecimal.valueOf(200_000L))
                .insurance(BigDecimal.valueOf(100_000L))
                .medicalExpenses(BigDecimal.valueOf(4_400_000L))
                .retirementPension(retirementPension)
                .totalPaymentAmount(BigDecimal.valueOf(60_000_000L))
                .build();

        BigDecimal taxAmount = tax.getTaxAmount();
        BigDecimal earnedIncomeTaxCreditAmount = taxAmount.multiply(BigDecimal.valueOf(0.55));
        BigDecimal insurancePremiumDeductionAmount = tax.getInsurance().multiply(BigDecimal.valueOf(0.12));

        BigDecimal medicalExpenseDeductibleAmount = tax.getMedicalExpenses().subtract(tax.getTotalPaymentAmount().multiply(BigDecimal.valueOf(0.03))).multiply(BigDecimal.valueOf(0.15));
        if(medicalExpenseDeductibleAmount.compareTo(BigDecimal.ZERO) < 0) medicalExpenseDeductibleAmount = BigDecimal.ZERO;

        BigDecimal educationalExpenseDeductionAmount = tax.getEducationExpenses().multiply(BigDecimal.valueOf(0.15));
        BigDecimal donationDeductionAmount = tax.getDonation().multiply(BigDecimal.valueOf(0.15));
        BigDecimal specialTaxDeductionAmount = insurancePremiumDeductionAmount.add(medicalExpenseDeductibleAmount).add(educationalExpenseDeductionAmount).add(donationDeductionAmount);

        BigDecimal standardTaxDeductionAmount = BigDecimal.ZERO;
        if(specialTaxDeductionAmount.compareTo(BigDecimal.valueOf(130_000L)) < 0) standardTaxDeductionAmount = BigDecimal.valueOf(130_000L);

        BigDecimal retirementPensionTaxDeductionAmount = retirementPension.multiply(BigDecimal.valueOf(0.15));
        BigDecimal sumAmount = retirementPensionTaxDeductionAmount.add(earnedIncomeTaxCreditAmount).add(specialTaxDeductionAmount).add(standardTaxDeductionAmount);

        BigDecimal determinedTaxAmount = taxAmount.subtract(sumAmount);
        if(determinedTaxAmount.compareTo(BigDecimal.ZERO) < 0) determinedTaxAmount = BigDecimal.ZERO;

        // when then
        assertThat(TaxCalculator.calculateDeterminedTaxAmount(retirementPensionTaxDeductionAmount, tax)).isEqualTo(determinedTaxAmount);
    }

}