package o3.member.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDeduction {

//    @JsonProperty("금액")
    @JsonAlias({"금액","총납임금액"})
    private String amount;

    @JsonProperty("소득구분")
    private String incomeClassification;
}
