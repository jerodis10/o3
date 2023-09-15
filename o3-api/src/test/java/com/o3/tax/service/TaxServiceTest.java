package com.o3.tax.service;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.domain.Member;
import com.o3.member.dto.Data;
import com.o3.member.dto.IncomeDeduction;
import com.o3.member.dto.JsonList;
import com.o3.member.dto.Salary;
import com.o3.member.repository.MemberRepository;
import com.o3.openfeign.MemberScrapClient;
import com.o3.security.common.AESUtil;
import com.o3.tax.domain.Tax;
import com.o3.tax.dto.response.TaxRefundResponse;
import com.o3.tax.dto.response.TaxScrapResponse;
import com.o3.tax.repository.TaxRepository;
import com.o3.tax.util.NumberUtil;
import com.o3.tax.util.TaxCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.o3.tax.util.TaxGroup.RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaxServiceTest {

    @InjectMocks
    private TaxService taxService;

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberScrapClient feign;

    @Test
    @DisplayName("저장되지 않은 회원의 스크랩 요청 예외")
    void throwException_whenNoMemberScrapTax() {
        // given
        String loginId = "a";
        given(memberRepository.findByLoginId(loginId)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> taxService.scrapTax(loginId)).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.NO_MEMBER.getMessage());
    }

    @Test
    @DisplayName("저장되지 않은 회원의 환급액 조회 예외")
    void throwException_whenNoMemberRefundTax() {
        // given
        String loginId = "a";
        given(memberRepository.findByLoginId(loginId)).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> taxService.refundTax(loginId)).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.NO_MEMBER.getMessage());
    }

    @Test
    @DisplayName("세금 테이블 내 저장되지 않은 회원의 환급액 조회 예외")
    void throwException_whenTaxNoMemberRefundTax() {
        // given
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .name("홍길동")
                .regNo(AESUtil.encrypt("860824-1655068"))
                .role("MEMBER")
                .build();

        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.ofNullable(member));

        // when then
        assertThatThrownBy(() -> taxService.refundTax(member.getLoginId())).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.NO_MEMBER.getMessage());
    }
    
    @Test
    @DisplayName("회원 스크랩 요청 테스트")
    void success_whenScrapTax() {
        // given
        ArgumentCaptor<Tax> captor = ArgumentCaptor.forClass(Tax.class);
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .name("홍길동")
                .regNo(AESUtil.encrypt("860824-1655068"))
                .role("MEMBER")
                .build();

        Tax tax = Tax.builder()
                .taxAmount(3_000_000L)
                .donation(150_000L)
                .educationExpenses(200_000L)
                .insurance(100_000L)
                .medicalExpenses(4_400_000L)
                .retirementPension(6_000_000L)
                .totalPaymentAmount(60_000_000L)
                .build();

        Salary salary = new Salary("홍길동", "860824 - 1655068", "60,000,000");
        List<Salary> salaryList = List.of(salary);
        IncomeDeduction income = new IncomeDeduction("100", "퇴직연금");
        List<IncomeDeduction> incomeDeductionList = List.of(income);
        JsonList jsonList = new JsonList(salaryList, incomeDeductionList, String.valueOf(tax.getTaxAmount()));
        Data data = new Data(jsonList);

        TaxScrapResponse taxScrapResponse = TaxScrapResponse.builder()
                .status("success")
                .data(data)
                .build();

        given(memberRepository.findByLoginId(any())).willReturn(Optional.ofNullable(member));
        given(feign.call(any())).willReturn(taxScrapResponse);
        given(taxRepository.save(any())).willReturn(null);
        given(memberRepository.findByNameAndRegNo(any(), any())).willReturn(Optional.ofNullable(member));

        // when
        taxService.scrapTax(member.getLoginId());

        // then
        verify(taxRepository, times(1)).save(captor.capture());
        Tax savedTax = captor.getValue();

        assertThat(savedTax.getTaxAmount()).isEqualTo(tax.getTaxAmount());
    }
    
    @Test
    @DisplayName("환급액 계산 테스트")
    void success_whenRefundTax() {
        // given
        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .name("홍길동")
                .regNo("860824-1655068")
                .role("MEMBER")
                .build();

        Tax tax = Tax.builder()
                .taxAmount(3_000_000L)
                .donation(150_000L)
                .educationExpenses(200_000L)
                .insurance(100_000L)
                .medicalExpenses(4_400_000L)
                .retirementPension(6_000_000L)
                .totalPaymentAmount(60_000_000L)
                .build();

        given(memberRepository.findByLoginId(any())).willReturn(Optional.ofNullable(member));
        given(taxRepository.findByMemberId(any())).willReturn(Optional.ofNullable(tax));

        long retirementPensionTaxDeductionAmount = RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT.calculateTax(tax.getRetirementPension(), 0L, 0L, 0L, 0L);
        long determinedTaxAmount = TaxCalculator.calculateDeterminedTaxAmount(retirementPensionTaxDeductionAmount, tax);

        // when
        TaxRefundResponse taxRefundResponse = taxService.refundTax(member.getLoginId());
        
        //then
        assertThat(taxRefundResponse.getRetirementPensionTaxCredit()).isEqualTo(NumberUtil.numberFormatter(retirementPensionTaxDeductionAmount));
        assertThat(taxRefundResponse.getDeterminedTaxAmount()).isEqualTo(NumberUtil.numberFormatter(determinedTaxAmount));
    }

}