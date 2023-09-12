package o3.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

    @JsonProperty("이름")
    private String name;

    @JsonProperty("주민등록번호")
    private String regNo;
}
