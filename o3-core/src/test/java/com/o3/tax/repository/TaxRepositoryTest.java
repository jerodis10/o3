package com.o3.tax.repository;

import com.o3.member.domain.Member;
import com.o3.member.repository.MemberRepository;
import com.o3.tax.domain.Tax;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaxRepositoryTest {

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private MemberRepository memberRepository;

    String loginId;

    @BeforeEach
    void setUp() {
        loginId = "a";

        Member member = Member.builder()
                .loginId(loginId)
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

        tax.updateMember(member);
        taxRepository.save(tax);
    }

    @AfterEach
    void end() {
        taxRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 아이디로 회원 조회 - 해당 회원 없는 경우")
    void whenCreateMemberByNameAndRegNo_noMember() {
        // when
        Optional<Tax> findTax = taxRepository.findByMemberId(-1L);

        //then
        assertThat(findTax).isEmpty();
    }

    @Test
    @DisplayName("이름, 주민등록번호로 회원 조회")
    void success_whenCreateMemberByNameAndRegNo() {
        // when
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);
        Optional<Tax> findTax = taxRepository.findByMemberId(findMember.get().getId());

        //then
        assertThat(findTax).isNotEmpty();
    }

}