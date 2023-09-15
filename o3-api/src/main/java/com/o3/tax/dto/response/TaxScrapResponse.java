package com.o3.tax.dto.response;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.dto.Data;
import com.o3.member.dto.IncomeDeduction;
import com.o3.member.dto.JsonList;
import com.o3.member.dto.Salary;
import com.o3.security.common.AESUtil;
import com.o3.tax.domain.Tax;
import com.o3.tax.util.NumberUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.o3.tax.util.PaymentGroup.*;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TaxScrapResponse {

    private final String status;
    private final Data data;

    public String getName() {
        List<Salary> salaryList = getSalaryList(data.getJsonList());
        if(!ObjectUtils.isEmpty(salaryList)) return salaryList.get(0).getName();
        return null;
    }

    public String getRegNo() {
        List<Salary> salaryList = getSalaryList(data.getJsonList());
        if(!ObjectUtils.isEmpty(salaryList)) return AESUtil.encrypt(salaryList.get(0).getRegNo());
        return null;
    }

    public Tax toEntity() {
        JsonList jsonList = data.getJsonList();

        String taxAmount = "";
        String retirementPension = "";
        String insurance = "";
        String medicalExpenses = "";
        String educationExpenses = "";
        String donation = "";
        long totalPaymentAmount = 0L;

        if (ObjectUtils.isEmpty(jsonList)) throw new O3Exception(O3ExceptionStatus.NO_JSON_EXTERNAL_API);

        taxAmount = jsonList.getTaxAmount();

        List<Salary> salaryList = jsonList.getSalaryList();
        if(salaryList.isEmpty()) throw new O3Exception(O3ExceptionStatus.NO_SALARY_EXTERNAL_API);

        totalPaymentAmount = sumTotalPaymentAmount(salaryList);

        List<IncomeDeduction> incomeDeductionList = jsonList.getIncomeDeductionList();
        if(incomeDeductionList.isEmpty()) throw new O3Exception(O3ExceptionStatus.NO_INCOME_EXTERNAL_API);

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
        }

        return Tax.builder()
                .taxAmount(NumberUtil.parseLong(taxAmount))
                .donation(NumberUtil.parseLong(donation))
                .medicalExpenses(NumberUtil.parseLong(medicalExpenses))
                .educationExpenses(NumberUtil.parseLong(educationExpenses))
                .insurance(NumberUtil.parseLong(insurance))
                .retirementPension(NumberUtil.parseLong(retirementPension))
                .totalPaymentAmount(totalPaymentAmount)
                .build();
    }

    private List<Salary> getSalaryList(JsonList jsonList) {
        if (!ObjectUtils.isEmpty(jsonList) && !jsonList.getSalaryList().isEmpty()) {
            return jsonList.getSalaryList();
        }
        return new ArrayList<>();
    }

    private long sumTotalPaymentAmount(List<Salary> salaryList) {
        long totalPaymentAmount = 0L;
        for (Salary salary : salaryList) {
            String s = salary.getTotalPaymentAmount().replace(",", "");
            if (StringUtils.hasText(s)) {
                totalPaymentAmount += Long.parseLong(s);
            }
        }
        return totalPaymentAmount;
    }

}
