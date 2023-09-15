package com.o3.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class JsonList {

    @JsonProperty("급여")
    private final List<Salary> salaryList;

    @JsonProperty("소득공제")
    private final List<IncomeDeduction> incomeDeductionList;

    @JsonProperty("산출세액")
    private final String taxAmount;

    public List<Salary> getSalaryList() {
        return List.copyOf(salaryList);
    }

    public List<IncomeDeduction> getIncomeDeductionList() {
        return List.copyOf(incomeDeductionList);
    }
}
