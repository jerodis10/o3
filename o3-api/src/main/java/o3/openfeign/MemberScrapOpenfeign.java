package o3.openfeign;

import o3.tax.dto.request.TaxScrapRequest;
import o3.tax.dto.response.TaxScrapResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "MemberScrapOpenfeign", url = "${scrap.member.api.uri}")
public interface MemberScrapOpenfeign {

    @PostMapping
    TaxScrapResponse call(@RequestBody TaxScrapRequest taxScrapRequest);
//    TaxScrapResponse call(@RequestBody TaxScrapRequest taxScrapRequest);
}
