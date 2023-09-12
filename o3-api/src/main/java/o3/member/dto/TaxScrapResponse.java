package o3.member.dto;

import lombok.*;
import o3.member.domain.Tax;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@ToString
public class TaxScrapResponse {

    private final String status;
    private final Data data;
//    private final LinkedHashMap<String, Object> data;

    public Tax toEntity() {
        JsonList jsonList = data.getJsonList();
        List<Salary> salaryList = new ArrayList<>();
        List<IncomeDeduction> incomeDeductionList = new ArrayList<>();

        String name = "";
        String regNo = "";
        String taxAmount = "";
        String retirementPension = "";
        String insurance = "";
        String medicalExpenses = "";
        String educationExpenses = "";
        String donation = "";

        if (ObjectUtils.isEmpty(jsonList)) {
            throw new RuntimeException();
        }

        salaryList = jsonList.getSalaryList();
        if (salaryList.size() == 0 || ObjectUtils.isEmpty(salaryList)) {
            throw new RuntimeException();
        }
        name = salaryList.get(0).getName();
        regNo = salaryList.get(0).getRegNo();

        if (!StringUtils.hasText(jsonList.getTaxAmount())) {
            throw new RuntimeException();
        }
        taxAmount = jsonList.getTaxAmount();

        incomeDeductionList = jsonList.getIncomeDeductionList();
        if (incomeDeductionList.size() == 0 || ObjectUtils.isEmpty(incomeDeductionList)) {
            throw new RuntimeException();
        }
        for (IncomeDeduction income : incomeDeductionList) {
            if (income.getIncomeClassification().equals("퇴직연금")) {
                retirementPension = income.getAmount();
            } else if (income.getIncomeClassification().equals("보험료")) {
                insurance = income.getAmount();
            } else if (income.getIncomeClassification().equals("교육비")) {
                educationExpenses = income.getAmount();
            } else if (income.getIncomeClassification().equals("의료비")) {
                medicalExpenses = income.getAmount();
            } else if (income.getIncomeClassification().equals("기부금")) {
                donation = income.getAmount();
            }
        }


        return Tax.builder()
                .name(name)
                .regNo(regNo)
                .taxAmount(taxAmount)
                .donation(donation)
                .medicalExpenses(medicalExpenses)
                .educationExpenses(educationExpenses)
                .insurance(insurance)
                .retirementPension(retirementPension)
                .build();
    }
}
