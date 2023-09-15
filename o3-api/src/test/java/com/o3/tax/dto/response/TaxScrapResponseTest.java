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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class TaxScrapResponseTest {

    Data data;
    String name, regNo;
    long taxAmount;
    String donation, educationExpenses, insurance, medicalExpenses, retirementPension, totalPaymentAmount;

    @BeforeEach
    void setUp() {
        taxAmount = 3_000_000L;
        donation = "150,000";
        educationExpenses = "200,000";
        insurance = "100,000";
        medicalExpenses = "4,400,000";
        retirementPension = "6,000,000";
        totalPaymentAmount = "60,000,000";

        Tax tax = Tax.builder()
                .taxAmount(taxAmount)
                .donation(150_000L)
                .educationExpenses(200_000L)
                .insurance(100_000L)
                .medicalExpenses(4_400_000L)
                .retirementPension(6_000_000L)
                .totalPaymentAmount(60_000_000L)
                .build();

        name = "홍길동";
        regNo = "860824 - 1655068";

        Salary salary = new Salary(name, regNo, "60,000,000");
        List<Salary> salaryList = List.of(salary);

        IncomeDeduction income = new IncomeDeduction(insurance, "보험료");
        IncomeDeduction income2 = new IncomeDeduction(educationExpenses, "교육비");
        IncomeDeduction income3 = new IncomeDeduction(donation, "기부금");
        IncomeDeduction income4 = new IncomeDeduction(medicalExpenses, "의료비");
        IncomeDeduction income5 = new IncomeDeduction(retirementPension, "퇴직연금");
        List<IncomeDeduction> incomeDeductionList = List.of(income, income2, income3, income4, income5);

        JsonList jsonList = new JsonList(salaryList, incomeDeductionList, String.valueOf(tax.getTaxAmount()));
        data = new Data(jsonList);
    }

    @Test
    @DisplayName("TaxScrapResponse_toEntity_noJsonList")
    void throwException_TaxScrapResponse_toEntity_noJsonList() {
        // given
        data = new Data(null);
        TaxScrapResponse taxScrapResponse = new TaxScrapResponse("success", data);

        // when then
        assertThatThrownBy(() -> taxScrapResponse.toEntity()).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.NO_JSON_EXTERNAL_API.getMessage());
    }

    @Test
    @DisplayName("TaxScrapResponse_toEntity_noSalary")
    void throwException_TaxScrapResponse_toEntity_noSalary() {
        // given
        Salary salary = new Salary(name, regNo, "60,000,000");
        List<Salary> salaryList = List.of(salary);
        JsonList jsonList = new JsonList(salaryList, new ArrayList<>(), "100");
        data = new Data(jsonList);
        TaxScrapResponse taxScrapResponse = new TaxScrapResponse("success", data);

        // when then
        assertThatThrownBy(() -> taxScrapResponse.toEntity()).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.NO_INCOME_EXTERNAL_API.getMessage());
    }

    @Test
    @DisplayName("TaxScrapResponse_toEntity_noIncome")
    void throwException_TaxScrapResponse_toEntity_noIncome() {
        // given
        JsonList jsonList = new JsonList(new ArrayList<>(), new ArrayList<>(), "100");
        data = new Data(jsonList);
        TaxScrapResponse taxScrapResponse = new TaxScrapResponse("success", data);

        // when then
        assertThatThrownBy(() -> taxScrapResponse.toEntity()).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.NO_SALARY_EXTERNAL_API.getMessage());
    }

    @Test
    @DisplayName("TaxScrapResponse_getName")
    void success_TaxScrapResponse_getName() {
        // given
        TaxScrapResponse taxScrapResponse = new TaxScrapResponse("success", data);

        // when
        String findName = taxScrapResponse.getName();

        //then
        assertThat(findName).isEqualTo(name);
    }

    @Test
    @DisplayName("TaxScrapResponse_getRegNo")
    void success_TaxScrapResponse_getRegNo() {
        // given
        TaxScrapResponse taxScrapResponse = new TaxScrapResponse("success", data);

        // when
        String findRegNo = taxScrapResponse.getRegNo();

        //then
        assertThat(findRegNo).isEqualTo(AESUtil.encrypt(regNo));
    }

    @Test
    @DisplayName("TaxScrapResponse_toEntity")
    void success_TaxScrapResponse_toEntity() {
        // given
        TaxScrapResponse taxScrapResponse = new TaxScrapResponse("success", data);

        // when
        Tax tax = taxScrapResponse.toEntity();

        //then
        assertThat(tax.getTaxAmount()).isEqualTo(taxAmount);
        assertThat(tax.getDonation()).isEqualTo(NumberUtil.parseLong(donation));
        assertThat(tax.getEducationExpenses()).isEqualTo(NumberUtil.parseLong(educationExpenses));
        assertThat(tax.getInsurance()).isEqualTo(NumberUtil.parseLong(insurance));
        assertThat(tax.getMedicalExpenses()).isEqualTo(NumberUtil.parseLong(medicalExpenses));
        assertThat(tax.getRetirementPension()).isEqualTo(NumberUtil.parseLong(retirementPension));
        assertThat(tax.getTotalPaymentAmount()).isEqualTo(NumberUtil.parseLong(totalPaymentAmount));
    }

}