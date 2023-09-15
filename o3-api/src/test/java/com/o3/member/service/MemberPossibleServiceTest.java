package com.o3.member.service;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.domain.MemberPossible;
import com.o3.member.repository.MemberPossibleRepository;
import com.o3.security.common.AESUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberPossibleServiceTest {

    @InjectMocks
    private MemberPossibleService memberPossibleService;

    @Mock
    private MemberPossibleRepository memberPossibleRepository;

    List<MemberPossible> memberList;
    MemberPossible memberPossible;

    @BeforeEach
    void setUp() {
        memberPossible = MemberPossible.builder()
                .name("홍길동")
                .regNo(AESUtil.encrypt("860824-1655068"))
                .build();
        memberList = List.of(memberPossible);
    }

    @Test
    @DisplayName("회원가입 가능 회원 중복 생성시 예외 발생")
    void throwException_whenCreateDuplicatePossibleMember() {
        // given
        given(memberPossibleRepository.findByNameAndRegNo(any(), any())).willReturn(Optional.ofNullable(memberPossible));

        // then
        assertThatThrownBy(() -> memberPossibleService.createPossibleMember(memberList)).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.DUPLICATION_MEMBER.getMessage());
//        assertThatThrownBy(() -> memberPossibleService.createPossibleMember(memberList)).isInstanceOf(O3Exception.class);
    }

    @Test
    @DisplayName("회원가입 가능 회원 생성 테스트")
    void success_whenCorrectPossibleMember() {
        // given
        ArgumentCaptor<MemberPossible> captor = ArgumentCaptor.forClass(MemberPossible.class);
        given(memberPossibleRepository.save(memberPossible)).willReturn(null);

        // when
        memberPossibleService.createPossibleMember(memberList);

        //then
        verify(memberPossibleRepository, times(1)).save(captor.capture());
        MemberPossible savedMember = captor.getValue();

        assertThat(savedMember.getName()).isEqualTo(memberPossible.getName());
        assertThat(savedMember.getRegNo()).isEqualTo(memberPossible.getRegNo());
    }

    @Test
    @DisplayName("회원가입 가능 회원 주민등록번호 저장시 암호화 테스트")
    void success_whenPossibleMemberRegNoEncrypted() {
        // given
        ArgumentCaptor<MemberPossible> captor = ArgumentCaptor.forClass(MemberPossible.class);
        given(memberPossibleRepository.save(memberPossible)).willReturn(null);

        // when
        memberPossibleService.createPossibleMember(memberList);

        //then
        verify(memberPossibleRepository, times(1)).save(captor.capture());
        MemberPossible savedMember = captor.getValue();
        String regNo = AESUtil.decrypt(savedMember.getRegNo());

        assertThat(regNo).matches("\\d{6}-\\d{7}");
    }
}