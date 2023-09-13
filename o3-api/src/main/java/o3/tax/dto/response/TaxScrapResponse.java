package o3.tax.dto.response;

import lombok.*;
import o3.exception.O3Exception;
import o3.exception.O3ExceptionStatus;
import o3.tax.domain.Tax;
import o3.member.dto.Data;
import o3.member.dto.IncomeDeduction;
import o3.member.dto.JsonList;
import o3.member.dto.Salary;
import o3.tax.util.PaymentGroup;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static o3.tax.util.PaymentGroup.*;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class TaxScrapResponse {

    private final String status;
    private final Data data;

    public Tax toEntity() {
        JsonList jsonList = data.getJsonList();

//        String name = "";
//        String regNo = "";
        String taxAmount = "";
        String retirementPension = "";
        String insurance = "";
        String medicalExpenses = "";
        String educationExpenses = "";
        String donation = "";
        long totalPaymentAmount = 0L;

        if (ObjectUtils.isEmpty(jsonList)) {
            throw new O3Exception(O3ExceptionStatus.NO_JSON_EXTERNAL_API);
        }

        List<Salary> salaryList = jsonList.getSalaryList();
        for (Salary salary : salaryList) {
//            name = salary.getName();
//            regNo = salary.getRegNo();

            String s = salary.getTotalPaymentAmount().replace(",", "");
            if (StringUtils.hasText(s)) {
                totalPaymentAmount += Long.parseLong(s);
            }
        }

//        if (salaryList.size() == 0 || ObjectUtils.isEmpty(salaryList)) {
//            throw new RuntimeException();
//        }
//        name = salaryList.get(0).getName();
//        regNo = salaryList.get(0).getRegNo();
//
//        for (Salary salary : salaryList) {
//            String s = salary.getTotalPaymentAmount().replace(",", "");
//            if (StringUtils.hasText(s)) {
//                totalPaymentAmount += Long.parseLong(s);
//            }
//        }

//        if (!StringUtils.hasText(jsonList.getTaxAmount())) {
//            throw new RuntimeException();
//        }

        taxAmount = jsonList.getTaxAmount();

        List<IncomeDeduction> incomeDeductionList = jsonList.getIncomeDeductionList();
//        if (incomeDeductionList.size() == 0 || ObjectUtils.isEmpty(incomeDeductionList)) {
//            throw new RuntimeException();
//        }

        for (IncomeDeduction income : incomeDeductionList) {
            String incomeClassification = income.getIncomeClassification();
            if (incomeClassification.equals(RETIREMENTPENSION.getTitle())) {
                retirementPension = income.getAmount();
            } else if (incomeClassification.equals(INSURANCE.getTitle())) {
                insurance = income.getAmount();
            } else if (incomeClassification.equals(EDUCATIONEXPENSES.getTitle())) {
                educationExpenses = income.getAmount();
            } else if (incomeClassification.equals(MEDICALEXPENSES.getTitle())) {
                medicalExpenses = income.getAmount();
            } else if (incomeClassification.equals(DONATION.getTitle())) {
                donation = income.getAmount();
            }


//            switch (income.getIncomeClassification()) {
//                case "퇴직연금":
//                    retirementPension = income.getAmount();
//                    break;
//                case "보험료":
//                    insurance = income.getAmount();
//                    break;
//                case "교육비":
//                    educationExpenses = income.getAmount();
//                    break;
//                case "의료비":
//                    medicalExpenses = income.getAmount();
//                    break;
//                case "기부금":
//                    donation = income.getAmount();
//                    break;
//            }


//            if (income.getIncomeClassification().equals(RETIREMENTPENSION.getTitle())) {
//                retirementPension = income.getAmount();
//            } else if (income.getIncomeClassification().equals("보험료")) {
//                insurance = income.getAmount();
//            } else if (income.getIncomeClassification().equals("교육비")) {
//                educationExpenses = income.getAmount();
//            } else if (income.getIncomeClassification().equals("의료비")) {
//                medicalExpenses = income.getAmount();
//            } else if (income.getIncomeClassification().equals("기부금")) {
//                donation = income.getAmount();
//            }


        }

        return Tax.builder()
//                .name(name)
//                .regNo(regNo)
                .taxAmount(parseLong(taxAmount))
                .donation(parseLong(donation))
                .medicalExpenses(parseLong(medicalExpenses))
                .educationExpenses(parseLong(educationExpenses))
                .insurance(parseLong(insurance))
                .retirementPension(parseLong(retirementPension))
                .totalPaymentAmount(totalPaymentAmount)
                .build();
    }

    private long parseLong(String formattedNumber) {
        if (StringUtils.hasText(formattedNumber)) {
            return Long.parseLong(formattedNumber.replace(",", ""));
        }
        return 0L;
    }
}
