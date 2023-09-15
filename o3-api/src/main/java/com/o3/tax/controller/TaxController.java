package com.o3.tax.controller;

import com.o3.security.jwt.JwtProvider;
import com.o3.tax.dto.response.TaxRefundResponse;
import com.o3.tax.service.TaxService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.o3.security.jwt.JwtFilter.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "스크랩", description = "외부 API 연동을 통한 가입한 유저 정보 스크랩", tags = { "Tax Controller" })
    @PostMapping("/scrap")
    public ResponseEntity<Void> scrapTax(
            @RequestHeader(AUTHORIZATION_HEADER) String jwtToken
//            HttpServletRequest request
    ) {
//        taxService.scrapTax(jwtProvider.getLoginId(request));
        taxService.scrapTax(jwtProvider.getLoginIdFromToken(jwtToken));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "세금 계산", description = "스크랩 정보를 바탕으로 결정세액과 퇴직연금세액 공제금 계산", tags = { "Tax Controller" })
    @GetMapping("/refund")
    public ResponseEntity<TaxRefundResponse> refundTax(
            @RequestHeader(AUTHORIZATION_HEADER) String jwtToken
//            HttpServletRequest request
    ) {
//        return ResponseEntity.ok(taxService.refundTax(jwtProvider.getLoginId(request)));
        return ResponseEntity.ok(taxService.refundTax(jwtProvider.getLoginIdFromToken(jwtToken)));
    }

}
