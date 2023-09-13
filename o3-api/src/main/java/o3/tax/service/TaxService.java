package o3.tax.service;

import lombok.RequiredArgsConstructor;
import o3.exception.O3Exception;
import o3.exception.O3ExceptionStatus;
import o3.member.domain.Member;
import o3.tax.domain.Tax;
import o3.tax.dto.response.TaxRefundResponse;
import o3.tax.dto.request.TaxScrapRequest;
import o3.member.repository.MemberRepository;
import o3.tax.repository.TaxRepository;
import o3.tax.dto.response.TaxScrapResponse;
import o3.openfeign.MemberScrapOpenfeign;
import o3.security.common.AESUtil;
import o3.security.jwt.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

import java.text.DecimalFormat;

import static o3.tax.util.SpecialTaxGroup.*;
import static o3.tax.util.TaxGroup.*;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final MemberScrapOpenfeign feign;

    public TaxScrapResponse scrapTax(HttpServletRequest request) {
        String loginId = getLoginId(request);

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER));

        TaxScrapRequest taxScrapRequest = TaxScrapRequest.builder()
                .name(member.getName())
                .regNo(AESUtil.decrypt(member.getRegNo()))
                .build();
        TaxScrapResponse taxScrapResponse = feign.call(taxScrapRequest);

        if (!"success".equals(taxScrapResponse.getStatus()) || ObjectUtils.isEmpty(taxScrapResponse.getData())) {
            throw new O3Exception(O3ExceptionStatus.FAIL_CONNECT_EXTERNAL_API);
        }

        Tax tax = taxScrapResponse.toEntity();

        tax.updateMember(memberRepository.findByNameAndRegNo(taxScrapRequest.getName(), AESUtil.encrypt(taxScrapRequest.getRegNo()))
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER)));

        taxRepository.save(tax);

        return taxScrapResponse;
    }

    public TaxRefundResponse refundTax(HttpServletRequest request) {
        String loginId = getLoginId(request);
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER));

        Tax tax = taxRepository.findByMemberId(member.getId());

        long retirementPensionTaxDeductionAmount = RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT.calculateTax(tax.getRetirementPension(), 0L, 0L, 0L, 0L, 0L);
        long determinedTaxAmount = calculateDeterminedTaxAmount(retirementPensionTaxDeductionAmount, tax);

//        long taxAmount = Long.valueOf(tax.getTaxAmount().replace(",", ""));
//        long earnedIncomeTaxCreditAmount = (long) (taxAmount * 0.55);
////        long retirementPensionTaxDeductionAmount = (long) (Long.valueOf(tax.getRetirementPension().replace(",", ""))  * 0.15); => null 처리 필요
//        long retirementPensionTaxDeductionAmount = 0L;
//        long insurancePremiumDeductionAmount = (long) (Long.valueOf(tax.getInsurance().replace(",", "")) * 0.12);
//        long medicalExpenseDeductibleAmount = (long) ((long) (Long.valueOf(tax.getMedicalExpenses().replace(",", ""))
//                - Long.valueOf(tax.getTotalPaymentAmount().replace(",", "")) * 0.03) * 0.15);
//        if(medicalExpenseDeductibleAmount < 0) medicalExpenseDeductibleAmount = 0L;
//        long educationalExpenseDeductionAmount = (long) (Long.valueOf(tax.getEducationExpenses().replace(",", "")) * 0.15);
//        long donationDeductionAmount = (long) (Long.valueOf(tax.getDonation().replace(",", "")) * 0.15);
//        long specialTaxDeductionAmount = insurancePremiumDeductionAmount + medicalExpenseDeductibleAmount + educationalExpenseDeductionAmount + donationDeductionAmount;
//        long standardTaxDeductionAmount = 0L;
//        if (specialTaxDeductionAmount < 130_000L) {
//            standardTaxDeductionAmount = 130_000L;
//        } else {
//            standardTaxDeductionAmount = 0L;
//        }
//
//        Long determinedTaxAmount = Long.valueOf(tax.getTaxAmount().replace(",", "")) - earnedIncomeTaxCreditAmount
//                - retirementPensionTaxDeductionAmount - specialTaxDeductionAmount - standardTaxDeductionAmount;
//        if(determinedTaxAmount < 0L) determinedTaxAmount = 0L;

        return TaxRefundResponse.builder()
                .name(member.getName())
                .determinedTaxAmount(numberFormatter(determinedTaxAmount))
                .retirementPensionTaxCredit(numberFormatter(retirementPensionTaxDeductionAmount))
                .build();
    }

    private String numberFormatter(Long number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        return decimalFormat.format(number);
    }

    private String getLoginId(HttpServletRequest request) {
        String jwtToken = jwtProvider.resolveToken(request);
        return jwtProvider.getUserIdFromToken(jwtToken);
    }

    private long calculateDeterminedTaxAmount(long retirementPensionTaxDeductionAmount, Tax tax) {
        long taxAmount = tax.getTaxAmount();

        long earnedIncomeTaxCreditAmount = EARNEDINCOME_TAX_CREDIT_AMOUNT.calculateTax(taxAmount, 0L, 0L, 0L, 0L, 0L);

        long insurancePremiumDeductionAmount = INSURANCE_DEDUCTION_AMOUNT.calculateTax(tax.getInsurance(), 0L);
        long medicalExpenseDeductibleAmount = MEDICAL_DEDUCTION_AMOUNT.calculateTax(tax.getMedicalExpenses(), 0L);
        long educationalExpenseDeductionAmount = EDUCATIONAL_DEDUCTION_AMOUNT.calculateTax(tax.getEducationExpenses(), 0L);
        long donationDeductionAmount = DONATION_DEDUCTION_AMOUNT.calculateTax(tax.getDonation(), 0L);

        long specialTaxDeductionAmount = SPECIAL_TAX_DEDUCTION_AMOUNT.calculateTax(
                insurancePremiumDeductionAmount, medicalExpenseDeductibleAmount, educationalExpenseDeductionAmount, donationDeductionAmount, 0L, 0L
        );

        long standardTaxDeductionAmount = STANDARD_TAX_DEDUCTION_AMOUNT.calculateTax(specialTaxDeductionAmount, 0L, 0L, 0L, 0L, 0L);

        return DETERMINED_TAX_AMOUNT.calculateTax(
                taxAmount,
                retirementPensionTaxDeductionAmount,
                earnedIncomeTaxCreditAmount,
                specialTaxDeductionAmount,
                standardTaxDeductionAmount,
                0L
        );
    }

}
