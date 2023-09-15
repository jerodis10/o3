package com.o3.openfeign.circuit;

import com.o3.openfeign.FallbackMemberScrapClient;
import com.o3.openfeign.MemberScrapClient;
import com.o3.tax.dto.request.TaxScrapRequest;
import com.o3.tax.dto.response.TaxScrapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberScrapClientFallbackFactory implements FallbackFactory<MemberScrapClient> {

    private final FallbackMemberScrapClient fallbackMemberScrapClient;

    @Override
    public MemberScrapClient create(Throwable cause) {
        log.error(cause.getMessage(), cause);
        return new MemberScrapClientFallback();
    }

    public class MemberScrapClientFallback implements MemberScrapClient {
        @Override
        public TaxScrapResponse call(TaxScrapRequest taxScrapRequest) {
            log.info("MemberScrapClientFallback");
            return fallbackMemberScrapClient.call(taxScrapRequest);
        }
    }

}
