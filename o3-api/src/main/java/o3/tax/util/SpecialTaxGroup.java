package o3.tax.util;

public enum SpecialTaxGroup {

    INSURANCE_DEDUCTION_AMOUNT("보험료공제금액", 0.12) {
        public long calculateTax(long amount, long total){ return (long) (amount * ratio); }
    },

    EDUCATIONAL_DEDUCTION_AMOUNT("교육비공제금액", 0.15) {
        public long calculateTax(long amount, long total){ return (long) (amount * ratio); }
    },

    DONATION_DEDUCTION_AMOUNT("기부금공제금액", 0.15) {
        public long calculateTax(long amount, long total){ return (long) (amount * ratio); }

    },

    MEDICAL_DEDUCTION_AMOUNT("의료비공제금액", 0.15) {
        public long calculateTax(long amount, long total) {
            long num = (long) ((amount - total * 0.3) * ratio);
            if(num < 0L) return 0L;
            else return num;
        }
    };

    public abstract long calculateTax(long amount, long total);

    private final String title;
    protected final double ratio;

    SpecialTaxGroup(String title, double ratio) {
        this.title = title;
        this.ratio = ratio;
    }
}
