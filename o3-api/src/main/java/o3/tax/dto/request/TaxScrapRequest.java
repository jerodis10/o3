package o3.tax.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Builder
public class TaxScrapRequest {


    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, max = 6, message = "이름은 2글자 이상, 6글자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "주민번호를 입력해주세요")
    @Size(min = 14, max = 14, message = " 주민등록번호는 -을 포함하여 14글자를 입력해주세요.")
    @Pattern(regexp = "\\d{2}([0]\\d|[1][0-2])([0][1-9]|[1-2]\\d|[3][0-1])[-]*[1-4]\\d{6}")
    private String regNo;


}
