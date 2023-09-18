package com.o3.tax.util;

import com.o3.tax.domain.Tax;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

import static com.o3.tax.util.SpecialTaxGroup.*;
import static com.o3.tax.util.TaxGroup.*;

@UtilityClass
public class TaxCalculator {

    public BigDecimal calculateDeterminedTaxAmount(BigDecimal retirementPensionTaxDeductionAmount, Tax tax) {
        BigDecimal taxAmount = tax.getTaxAmount();

        BigDecimal earnedIncomeTaxCreditAmount = EARNEDINCOME_TAX_CREDIT_AMOUNT.calculateTax(taxAmount, BigDecimal.ZERO);

        BigDecimal insurancePremiumDeductionAmount = INSURANCE_DEDUCTION_AMOUNT.calculateTax(tax.getInsurance(), BigDecimal.ZERO);
        BigDecimal medicalExpenseDeductibleAmount = MEDICAL_DEDUCTION_AMOUNT.calculateTax(tax.getMedicalExpenses(), tax.getTotalPaymentAmount());
        BigDecimal educationalExpenseDeductionAmount = EDUCATIONAL_DEDUCTION_AMOUNT.calculateTax(tax.getEducationExpenses(), BigDecimal.ZERO);
        BigDecimal donationDeductionAmount = DONATION_DEDUCTION_AMOUNT.calculateTax(tax.getDonation(), BigDecimal.ZERO);

        BigDecimal specialTaxDeductionAmount = SPECIAL_TAX_DEDUCTION_AMOUNT.calculateTax(
                getSumAmount(insurancePremiumDeductionAmount, medicalExpenseDeductibleAmount, educationalExpenseDeductionAmount, donationDeductionAmount), BigDecimal.ZERO);

        BigDecimal standardTaxDeductionAmount = STANDARD_TAX_DEDUCTION_AMOUNT.calculateTax(specialTaxDeductionAmount, BigDecimal.ZERO);

        return DETERMINED_TAX_AMOUNT.calculateTax(taxAmount,
                getSumAmount(retirementPensionTaxDeductionAmount, earnedIncomeTaxCreditAmount, specialTaxDeductionAmount, standardTaxDeductionAmount));
    }

    private BigDecimal getSumAmount(BigDecimal... amountList) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal amount : amountList) {
            result = result.add(amount);
        }
        return result;
    }
}
