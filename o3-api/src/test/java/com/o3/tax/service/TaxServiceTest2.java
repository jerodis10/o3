//package com.o3.tax.service;
//
//import com.o3.exception.O3Exception;
//import com.o3.exception.O3ExceptionStatus;
//import com.o3.member.domain.Member;
//import com.o3.member.repository.MemberRepository;
//import com.o3.security.common.AESUtil;
//import com.o3.tax.domain.Tax;
//import com.o3.tax.dto.response.TaxRefundResponse;
//import com.o3.tax.repository.TaxRepository;
//import com.o3.tax.util.NumberUtil;
//import com.o3.tax.util.TaxCalculator;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//
//import static com.o3.tax.util.TaxGroup.RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//@Transactional
//class TaxServiceTest2 {
//
//    @Autowired
//    private TaxService taxService;
//
//    @Autowired
//    private TaxRepository taxRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
////    @Autowired
////    private MemberPossibleRepository memberPossibleRepository;
//
////    @BeforeEach
////    void setUp() {
////        MemberPossible memberPossible = MemberPossible.builder()
////                .name("홍길동")
////                .regNo(AESUtil.encrypt("860824-1655068"))
////                .build();
////
////        memberPossibleRepository.save(memberPossible);
////    }
//
//    @Test
//    @DisplayName("저장되지 않은 회원의 스크랩 요청 예외")
//    void throwException_whenNoMemberScrapTax() {
//        // when then
//        assertThatThrownBy(() -> taxService.scrapTax("b")).isInstanceOf(O3Exception.class);
//    }
//
//    @Test
//    @DisplayName("저장되지 않은 회원의 환급액 조회 예외")
//    void throwException_whenNoMemberRefundTax() {
//        //then
//        assertThatThrownBy(() -> taxService.refundTax("b")).isInstanceOf(O3Exception.class);
//    }
//
//    @Test
//    @DisplayName("세금 테이블 내 저장되지 않은 회원의 환급액 조회 예외")
//    void throwException_whenTaxNoMemberRefundTax() {
//        // given
//        Member member = Member.builder()
//                .loginId("a")
//                .password("a")
//                .name("홍길동")
//                .regNo(AESUtil.encrypt("860824-1655068"))
//                .role("MEMBER")
//                .build();
//
//        memberRepository.save(member);
//
//        //then
//        assertThatThrownBy(() -> taxService.refundTax(member.getLoginId())).isInstanceOf(O3Exception.class);
//    }
//
//    @Test
//    @DisplayName("회원 스크랩 요청 테스트")
//    void success_whenScrapTax() {
//        // given
//        Member member = Member.builder()
//                .loginId("a")
//                .password("a")
//                .name("홍길동")
//                .regNo(AESUtil.encrypt("860824-1655068"))
//                .role("MEMBER")
//                .build();
//
//        memberRepository.save(member);
//
//        // when
//        taxService.scrapTax(member.getLoginId());
//        Tax tax = taxRepository.findByMemberId(member.getId())
//                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER));
//
//        //then
//        assertThat(tax.getTaxAmount()).isEqualTo(3_000_000L);
//        assertThat(tax.getDonation()).isEqualTo(150_000L);
//        assertThat(tax.getEducationExpenses()).isEqualTo(200_000L);
//        assertThat(tax.getInsurance()).isEqualTo(100_000L);
//        assertThat(tax.getMedicalExpenses()).isEqualTo(4_400_000L);
//        assertThat(tax.getRetirementPension()).isEqualTo(6_000_000L);
//        assertThat(tax.getTotalPaymentAmount()).isEqualTo(60_000_000L);
//    }
//
//    @Test
//    @DisplayName("스크랩 요청 테스트")
//    void success_whenRefundTax() {
//        // given
//        Member member = Member.builder()
//                .loginId("a")
//                .password("a")
//                .name("홍길동")
//                .regNo("860824-1655068")
//                .role("MEMBER")
//                .build();
//
//        memberRepository.save(member);
//
//        Tax tax = Tax.builder()
//                .taxAmount(3_000_000)
//                .donation(150_000)
//                .educationExpenses(200_000)
//                .insurance(100_000)
//                .medicalExpenses(4_400_000)
//                .retirementPension(6_000_000)
//                .totalPaymentAmount(60_000_000)
//                .build();
//
//        tax.updateMember(member);
//        taxRepository.save(tax);
//
//        long retirementPensionTaxDeductionAmount = RETIREMENT_PENSION_TAX_DEDUCTION_AMOUNT.calculateTax(tax.getRetirementPension(), 0L, 0L, 0L, 0L);
//        long determinedTaxAmount = TaxCalculator.calculateDeterminedTaxAmount(retirementPensionTaxDeductionAmount, tax);
//
//        // when
//        TaxRefundResponse taxRefundResponse = taxService.refundTax(member.getLoginId());
//
//        //then
//        assertThat(taxRefundResponse.getRetirementPensionTaxCredit()).isEqualTo(NumberUtil.numberFormatter(retirementPensionTaxDeductionAmount));
//        assertThat(taxRefundResponse.getDeterminedTaxAmount()).isEqualTo(NumberUtil.numberFormatter(determinedTaxAmount));
//    }
//
////    @Test
////    @DisplayName("스크랩 요청 실패 예외")
////    void throwException_whenFailScrapTax() {
////        // given
////        Member member = Member.builder()
////                .loginId("a")
////                .password("a")
////                .name("홍길동")
////                .regNo("860824-1655068")
////                .role("MEMBER")
////                .build();
////
////        Tax tax = Tax.builder()
////                .taxAmount(3_000_000)
////                .donation(150_000)
////                .educationExpenses(200_000)
////                .insurance(100_000)
////                .medicalExpenses(4_400_000)
////                .retirementPension(6_000_000)
////                .totalPaymentAmount(60_000_000)
////                .build();
////
////        memberRepository.save(member);
////        taxRepository.save(tax);
////
////        // when
////
////
////        //then
////        assertThatThrownBy(() -> ).isInstanceOf(Exception.class);
////    }
//
//}