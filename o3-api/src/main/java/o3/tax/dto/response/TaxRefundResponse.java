package o3.tax.dto.response;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
public class TaxRefundResponse {


    private String name;

    private String determinedTaxAmount;

    private String retirementPensionTaxCredit;


}
