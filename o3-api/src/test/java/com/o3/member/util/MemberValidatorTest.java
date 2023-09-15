package com.o3.member.util;

import com.o3.member.domain.MemberPossible;
import com.o3.exception.O3Exception;
import com.o3.member.domain.Member;
import com.o3.security.common.AESUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberValidatorTest {
    
    @Test
    @DisplayName("저장되지 않은 회원 유효성 체크")
    void throwException_whenValidateMember() {
        // given
        MemberPossible memberPossible = MemberPossible.builder()
                .name("홍길동")
                .regNo(AESUtil.encrypt("860824-1655060"))
                .build();
        List<MemberPossible> memberList = List.of(memberPossible);

        Member member = Member.builder()
                .loginId("a")
                .password("a")
                .name("홍길동")
                .regNo("860824-1655068")
                .role("MEMBER")
                .build();
        
        // when then
        assertThatThrownBy(() -> MemberValidator.validateCreation(memberList, member)).isInstanceOf(O3Exception.class);
    }
}