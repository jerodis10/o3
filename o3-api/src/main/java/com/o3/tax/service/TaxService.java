package com.o3.tax.service;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.domain.Member;
import com.o3.member.repository.MemberRepository;
import com.o3.openfeign.MemberScrapClient;
import com.o3.tax.domain.Tax;
import com.o3.tax.dto.request.TaxScrapRequest;
import com.o3.tax.dto.response.TaxRefundResponse;
import com.o3.tax.dto.response.TaxScrapResponse;
import com.o3.tax.repository.TaxRepository;
import com.o3.tax.util.TaxCalculator;
import com.o3.tax.util.TaxGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private final MemberRepository memberRepository;
    private final MemberScrapClient feign;

    @Transactional
    public void scrapTax(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER));

        TaxScrapResponse taxScrapResponse = feign.call(TaxScrapRequest.of(member));

        if (taxScrapResponse == null || !"success".equals(taxScrapResponse.getStatus()) || ObjectUtils.isEmpty(taxScrapResponse.getData())) {
            throw new O3Exception(O3ExceptionStatus.FAIL_CONNECT_EXTERNAL_API);
        }

        Tax tax = taxScrapResponse.toEntity();

        tax.updateMember(memberRepository.findByNameAndRegNo(taxScrapResponse.getName(), taxScrapResponse.getRegNo())
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER)));

        taxRepository.save(tax);
    }

    @Cacheable(value = "tax", key = "#loginId")
    @Transactional(readOnly = true)
    public TaxRefundResponse refundTax(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER));

        Tax tax = taxRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER));

        long retirementPensionTaxDeductionAmount = TaxGroup.RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT.calculateTax(tax.getRetirementPension(), 0L, 0L, 0L, 0L);
        long determinedTaxAmount = TaxCalculator.calculateDeterminedTaxAmount(retirementPensionTaxDeductionAmount, tax);

        return TaxRefundResponse.of(member.getName(), retirementPensionTaxDeductionAmount, determinedTaxAmount);
    }

}
