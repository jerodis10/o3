package o3.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JsonList {

    @JsonProperty("급여")
    private List<Salary> salaryList;

    @JsonProperty("소득공제")
    private List<IncomeDeduction> incomeDeductionList;

    @JsonProperty("산출세액")
    private String taxAmount;
}
