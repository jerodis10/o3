package com.o3.member.repository;

import com.o3.member.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    String loginId, name, regNo;

    @BeforeEach
    void setUp() {
        loginId = "a";
        name = "홍길동";
        regNo = "860824-1655068";

        Member member = Member.builder()
                .loginId(loginId)
                .password("a")
                .name(name)
                .regNo(regNo)
                .role("MEMBER")
                .build();

        memberRepository.save(member);
    }

    @AfterEach
    void end() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 - 해당 회원 없는 경우")
    void whenCreateMemberByloginId_noMember() {
        // when
        Optional<Member> findMember = memberRepository.findByLoginId("b");

        //then
        assertThat(findMember).isEmpty();
    }

    @Test
    @DisplayName("이름, 주민등록번호로 회원 조회 - 해당 회원 없는 경우")
    void success_whenCreateMemberByNameAndRegNo_noMember() {
        // when
        Optional<Member> findMember = memberRepository.findByNameAndRegNo("b", regNo);

        //then
        assertThat(findMember).isEmpty();
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회")
    void success_whenCreateMemberByloginId() {
        // when
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);

        //then
        assertThat(findMember).isNotEmpty();
    }

    @Test
    @DisplayName("이름, 주민등록번호로 회원 조회")
    void success_whenCreateMemberByNameAndRegNo() {
        // when
        Optional<Member> findMember = memberRepository.findByNameAndRegNo(name, regNo);

        //then
        assertThat(findMember).isNotEmpty();
    }

}