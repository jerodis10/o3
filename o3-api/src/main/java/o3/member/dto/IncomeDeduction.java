package o3.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDeduction {

    @JsonProperty("금액")
    private String amount;

    @JsonProperty("소득구분")
    private String IncomeClassification;
}
