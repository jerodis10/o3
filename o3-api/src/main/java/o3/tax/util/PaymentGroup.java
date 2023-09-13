package o3.tax.util;

public enum PaymentGroup {
    INSURANCE("보험료"),
    MEDICALEXPENSES("의료비"),
    EDUCATIONEXPENSES("교육비"),
    DONATION("기부금"),
    RETIREMENTPENSION("퇴직연금");

    private final String title;

    PaymentGroup(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
