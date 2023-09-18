package com.o3.member.service;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.domain.Member;
import com.o3.member.domain.MemberPossible;
import com.o3.member.repository.MemberPossibleRepository;
import com.o3.member.repository.MemberRepository;
import com.o3.security.common.AESUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberPossibleRepository memberPossibleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .loginId("a")
                .password("a")
                .name("홍길동")
                .regNo("860824-1655068")
                .role("MEMBER")
                .build();
    }

    @Test
    @DisplayName("회원 중복 생성시 예외 발생")
    void throwException_whenCreateDuplicateMember() {
        // given
        MemberPossible memberPossible = MemberPossible.builder().name(member.getName()).regNo(AESUtil.encrypt(member.getRegNo())).build();
        List<MemberPossible> memberPossibleList = List.of(memberPossible);

        given(memberPossibleRepository.findAll()).willReturn(memberPossibleList);
        given(memberRepository.findByLoginId(any())).willReturn(Optional.ofNullable(member));

        // when then
        assertThatThrownBy(() -> memberService.createMember(member)).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.DUPLICATION_MEMBER.getMessage());
    }

    @Test
    @DisplayName("회원가입 불가능한 회원 생성시 예외 발생")
    void throwException_whenCreateImpossibleMember() {
        // given
        MemberPossible memberPossible = MemberPossible.builder().name("홍길동").regNo(AESUtil.encrypt("860824-1655060")).build();

        List<MemberPossible> memberPossibleList = List.of(memberPossible);
        given(memberPossibleRepository.findAll()).willReturn(memberPossibleList);

        // when then
        assertThatThrownBy(() -> memberService.createMember(member)).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.INVALID_MEMBER.getMessage());
    }

    @Test
    @DisplayName("JWT 토큰 정보로 저장되지 않은 회원 조회시 예외")
    void throwException_whenSearchMemberWithJWT() {
        // given
        given(memberRepository.findByLoginId(member.getLoginId())).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> memberService.searchDetailMember(member.getLoginId())).isInstanceOf(O3Exception.class).hasMessage(O3ExceptionStatus.NO_MEMBER.getMessage());
    }

    @Test
    @DisplayName("회원 생성 테스트")
    void success_whenCorrectMember() {
        // given
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        MemberPossible memberPossible = MemberPossible.builder().name(member.getName()).regNo(AESUtil.encrypt(member.getRegNo())).build();
        List<MemberPossible> memberPossibleList = List.of(memberPossible);

        given(memberPossibleRepository.findAll()).willReturn(memberPossibleList);
        given(memberRepository.save(member)).willReturn(null);

        // when
        memberService.createMember(member);

        //then
        verify(memberRepository, times(1)).save(captor.capture());
        Member savedMember = captor.getValue();

        assertThat(savedMember.getLoginId()).isEqualTo(member.getLoginId());
        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(savedMember.getName()).isEqualTo(member.getName());
        assertThat(savedMember.getRole()).isEqualTo(member.getRole());
        assertThat(savedMember.getRegNo()).isEqualTo(member.getRegNo());
    }

    @Test
    @DisplayName("회원 주민등록번호 저장시 암호화 테스트")
    void success_whenMemberRegNoEncrypted() {
        // given
        ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
        MemberPossible memberPossible = MemberPossible.builder().name(member.getName()).regNo(AESUtil.encrypt(member.getRegNo())).build();
        List<MemberPossible> memberPossibleList = List.of(memberPossible);

        given(memberPossibleRepository.findAll()).willReturn(memberPossibleList);
        given(memberRepository.save(member)).willReturn(null);

        // when
        memberService.createMember(member);

        //then
        verify(memberRepository, times(1)).save(captor.capture());
        Member savedMember = captor.getValue();
        String regNo = AESUtil.decrypt(savedMember.getRegNo());

        assertThat(regNo).matches("\\d{6}-\\d{7}");
    }
    
    @Test
    @DisplayName("JWT 토큰 정보로 회원 조회")
    void success_whenSearchMemberWithJWT() {
        // given
        Member m = Member.builder()
                .loginId("a")
                .password("a")
                .name("홍길동")
                .regNo(AESUtil.encrypt("860824-1655068"))
                .role("MEMBER")
                .build();

        given(memberRepository.findByLoginId(m.getLoginId())).willReturn(Optional.ofNullable(m));

        // when then
        assertThat(memberService.searchDetailMember(m.getLoginId())).isNotNull();
    }

}