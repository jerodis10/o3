package com.o3.tax.util;

import com.o3.tax.domain.Tax;
import lombok.experimental.UtilityClass;

import static com.o3.tax.util.SpecialTaxGroup.*;
import static com.o3.tax.util.SpecialTaxGroup.DONATION_DEDUCTION_AMOUNT;
import static com.o3.tax.util.TaxGroup.*;
import static com.o3.tax.util.TaxGroup.DETERMINED_TAX_AMOUNT;

@UtilityClass
public class TaxCalculator {

    public long calculateDeterminedTaxAmount(long retirementPensionTaxDeductionAmount, Tax tax) {
        long taxAmount = tax.getTaxAmount();

        long earnedIncomeTaxCreditAmount = EARNEDINCOME_TAX_CREDIT_AMOUNT.calculateTax(taxAmount, 0L, 0L, 0L, 0L);

        long insurancePremiumDeductionAmount = INSURANCE_DEDUCTION_AMOUNT.calculateTax(tax.getInsurance(), 0L);
        long medicalExpenseDeductibleAmount = MEDICAL_DEDUCTION_AMOUNT.calculateTax(tax.getMedicalExpenses(), tax.getTotalPaymentAmount());
        long educationalExpenseDeductionAmount = EDUCATIONAL_DEDUCTION_AMOUNT.calculateTax(tax.getEducationExpenses(), 0L);
        long donationDeductionAmount = DONATION_DEDUCTION_AMOUNT.calculateTax(tax.getDonation(), 0L);

        long specialTaxDeductionAmount = SPECIAL_TAX_DEDUCTION_AMOUNT.calculateTax(
                insurancePremiumDeductionAmount, medicalExpenseDeductibleAmount, educationalExpenseDeductionAmount, donationDeductionAmount, 0L);

        long standardTaxDeductionAmount = STANDARD_TAX_DEDUCTION_AMOUNT.calculateTax(specialTaxDeductionAmount, 0L, 0L, 0L, 0L);

        return DETERMINED_TAX_AMOUNT.calculateTax(
                taxAmount,
                retirementPensionTaxDeductionAmount,
                earnedIncomeTaxCreditAmount,
                specialTaxDeductionAmount,
                standardTaxDeductionAmount
        );
    }
}
