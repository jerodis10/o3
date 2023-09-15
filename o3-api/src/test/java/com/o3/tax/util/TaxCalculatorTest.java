package com.o3.tax.util;

import com.o3.tax.domain.Tax;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaxCalculatorTest {

    @Test
    @DisplayName("결정세액 계산")
    void success_whenCalculateTax() {
        // given
        long retirementPension = 6_000_000L;
        Tax tax = Tax.builder()
                .taxAmount(3_000_000L)
                .donation(150_000L)
                .educationExpenses(200_000L)
                .insurance(100_000L)
                .medicalExpenses(4_400_000L)
                .retirementPension(retirementPension)
                .totalPaymentAmount(60_000_000L)
                .build();

        long taxAmount = tax.getTaxAmount();
        long earnedIncomeTaxCreditAmount = (long) (taxAmount * 0.55);
        long insurancePremiumDeductionAmount = (long) (tax.getInsurance() * 0.12);

        long medicalExpenseDeductibleAmount = (long) ((tax.getMedicalExpenses() - tax.getTotalPaymentAmount() * 0.03) * 0.15);
        if(medicalExpenseDeductibleAmount < 0) medicalExpenseDeductibleAmount = 0L;

        long educationalExpenseDeductionAmount = (long) (tax.getEducationExpenses() * 0.15);
        long donationDeductionAmount = (long) (tax.getDonation() * 0.15);
        long specialTaxDeductionAmount = insurancePremiumDeductionAmount + medicalExpenseDeductibleAmount + educationalExpenseDeductionAmount + donationDeductionAmount;

        long standardTaxDeductionAmount = 0L;
        if(specialTaxDeductionAmount < 130_000L) standardTaxDeductionAmount = 130_000L;
        else standardTaxDeductionAmount = 0L;

        long retirementPensionTaxDeductionAmount =(long)(retirementPension * 0.15);

        long determinedTaxAmount = taxAmount - retirementPensionTaxDeductionAmount - earnedIncomeTaxCreditAmount - specialTaxDeductionAmount - standardTaxDeductionAmount;

        // when then
        assertThat(TaxCalculator.calculateDeterminedTaxAmount(retirementPensionTaxDeductionAmount, tax)).isEqualTo(determinedTaxAmount);
    }

}