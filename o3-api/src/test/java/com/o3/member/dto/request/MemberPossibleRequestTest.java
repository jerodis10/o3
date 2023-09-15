package com.o3.member.dto.request;

import com.o3.member.domain.MemberPossible;
import com.o3.security.common.AESUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberPossibleRequestTest {

    @Test
    @DisplayName("MemberPossible entity 변환")
    void success_MemberPossibleRequestToEntity() {
        // given
        String name = "홍길동";
        String regNo = "860824-1655068";
        MemberPossibleRequest memberPossibleRequest = new MemberPossibleRequest(name, regNo);

        // when
        MemberPossible memberPossible = memberPossibleRequest.toEntity();

        //then
        assertThat(memberPossible.getName()).isEqualTo(name);
        assertThat(memberPossible.getRegNo()).isEqualTo(AESUtil.encrypt(regNo));
    }

    @Test
    @DisplayName("MemberPossible list 변환")
    void success_MemberPossibleRequestMakeList() {
        // given
        String name = "홍길동";
        String regNo = "860824-1655068";
        List<MemberPossibleRequest> requestList = List.of(
                new MemberPossibleRequest("홍길동", "860824-1655068"),
                new MemberPossibleRequest("김둘리", "921108-1582816"));

        // when
        List<MemberPossible> memberPossibleList = MemberPossibleRequest.makeList(requestList);

        //then
        assertThat(memberPossibleList.size()).isEqualTo(requestList.size());
        assertThat(memberPossibleList.get(0).getName()).isEqualTo(requestList.get(0).getName());
        assertThat(memberPossibleList.get(0).getRegNo()).isEqualTo(AESUtil.encrypt(requestList.get(0).getRegNo()));
        assertThat(memberPossibleList.get(1).getName()).isEqualTo(requestList.get(1).getName());
        assertThat(memberPossibleList.get(1).getRegNo()).isEqualTo(AESUtil.encrypt(requestList.get(1).getRegNo()));
    }
}