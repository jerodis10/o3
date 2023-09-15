package com.o3.member.repository;

import com.o3.member.domain.Member;
import com.o3.member.domain.MemberPossible;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberPossibleRepositoryTest {

    @Autowired
    private MemberPossibleRepository memberPossibleRepository;

    String loginId, name, regNo;

    @BeforeEach
    void setUp() {
        name = "홍길동";
        regNo = "860824-1655068";

        MemberPossible memberPossible = MemberPossible.builder()
                .name(name)
                .regNo(regNo)
                .build();

        memberPossibleRepository.save(memberPossible);
    }

    @AfterEach
    void end() {
        memberPossibleRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회 - 해당 회원 없는 경우")
    void whenCreateMemberByNameAndRegNo_noMember() {
        // when
        Optional<MemberPossible> findMember = memberPossibleRepository.findByNameAndRegNo("b", regNo);

        //then
        assertThat(findMember).isEmpty();
    }

    @Test
    @DisplayName("이름, 주민등록번호로 회원 조회")
    void success_whenCreateMemberByNameAndRegNo() {
        // when
        Optional<MemberPossible> findMember = memberPossibleRepository.findByNameAndRegNo(name, regNo);

        //then
        assertThat(findMember).isNotEmpty();
    }

}