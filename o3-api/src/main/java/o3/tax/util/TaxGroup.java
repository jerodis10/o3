package o3.tax.util;

public enum TaxGroup {

    DETERMINED_TAX_AMOUNT("결정세액", 0) {
        public long calculateTax(long taxAmount,
                          long retirementPensionTaxDeductionAmount,
                          long earnedIncomeTaxCreditAmount,
                          long specialTaxDeductionAmount,
                          long standardTaxDeductionAmount,
                          long ratio) {
            return taxAmount - retirementPensionTaxDeductionAmount - earnedIncomeTaxCreditAmount -
                    - specialTaxDeductionAmount - standardTaxDeductionAmount;
        }
    },

    EARNEDINCOME_TAX_CREDIT_AMOUNT("근로소득세액공제금액", 0.55) {
        public long calculateTax(long taxAmount, long amount2, long amount3, long amount4, long amount5, long ratio) {
            return taxAmount * ratio;
        }
    },

    SPECIAL_TAX_DEDUCTION_AMOUNT("특별세액공제금액", 0) {
        public long calculateTax(long insuranceDeductionAmount,
                          long educationalDeductionAmount,
                          long donationDeductionAmount,
                          long medicalDeductionAmount,
                          long amount5, long ratio) {
            return insuranceDeductionAmount + educationalDeductionAmount + donationDeductionAmount + medicalDeductionAmount;
        }
    },

    RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT("퇴직연금세액공제금액", 0.15) {
        public long calculateTax(long retirementAmount, long amount2, long amount3, long amount4, long amount5, long ratio) {
            return retirementAmount * ratio;
        }
    },

    STANDARD_TAX_DEDUCTION_AMOUNT("표준세액공제금액", 130_000) {
        public long calculateTax(long specialTaxDeductionAmount, long amount2, long amount3, long amount4, long amount5, long ratio) {
            if(specialTaxDeductionAmount < ratio) return ratio;
            else return 0L;
        }
    };

    public abstract long calculateTax(long amount, long amount2, long amount3, long amount4, long amount5, long ratio);

    private final String title;
    protected final double ratio;

    TaxGroup(String title, double ratio) {
        this.title = title;
        this.ratio = ratio;
    }
}
