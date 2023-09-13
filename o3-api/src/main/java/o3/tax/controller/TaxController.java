package o3.tax.controller;

import lombok.RequiredArgsConstructor;
import o3.tax.service.TaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping("/scrap")
    public ResponseEntity<?> scrapTax(HttpServletRequest request) {
        return ResponseEntity.ok(taxService.scrapTax(request));
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundTax(HttpServletRequest request) {
        return ResponseEntity.ok(taxService.refundTax(request));
    }


}
