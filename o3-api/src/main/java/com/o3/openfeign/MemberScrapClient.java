package com.o3.openfeign;

import com.o3.openfeign.circuit.MemberScrapClientFallbackFactory;
import com.o3.tax.dto.request.TaxScrapRequest;
import com.o3.tax.dto.response.TaxScrapResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MemberScrapClient", url = "${scrap.member.api.uri}"
        , fallbackFactory = MemberScrapClientFallbackFactory.class
)
public interface MemberScrapClient {

    @PostMapping
    TaxScrapResponse call(@RequestBody TaxScrapRequest taxScrapRequest);

}



