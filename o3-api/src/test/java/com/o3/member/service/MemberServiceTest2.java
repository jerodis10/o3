//package com.o3.member.service;
//
//import com.o3.member.domain.MemberPossible;
//import com.o3.member.repository.MemberPossibleRepository;
//import com.o3.exception.O3Exception;
//import com.o3.member.domain.Member;
//import com.o3.member.repository.MemberRepository;
//import com.o3.security.common.AESUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//@Transactional
//class MemberServiceTest2 {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private MemberPossibleRepository memberPossibleRepository;
//
//    @Autowired
//    private MemberService memberService;
//
//    Member member;
//
//    @BeforeEach
//    void setUp() {
//        member = Member.builder()
//                .loginId("a")
//                .password("a")
//                .name("홍길동")
//                .regNo("860824-1655068")
//                .role("MEMBER")
//                .build();
//
//        MemberPossible memberPossible = MemberPossible.builder()
//                .name("홍길동")
//                .regNo(AESUtil.encrypt("860824-1655068"))
//                .build();
//
//        memberPossibleRepository.save(memberPossible);
//    }
//
//    @Test
//    @DisplayName("회원 중복 생성시 예외 발생")
//    void throwException_whenCreateDuplicateMember() {
//        // given
//        memberRepository.save(member);
//
//        // when then
//        assertThatThrownBy(() -> memberService.createMember(member)).isInstanceOf(O3Exception.class);
//    }
//
//    @Test
//    @DisplayName("회원가입 불가능한 회원 생성시 예외 발생")
//    void throwException_whenCreateImpossibleMember() {
//        // given
//        memberPossibleRepository.deleteAll();
//
//        MemberPossible memberPossible = MemberPossible.builder()
//                .name("홍길동")
//                .regNo(AESUtil.encrypt("860824-1655060"))
//                .build();
//        memberPossibleRepository.save(memberPossible);
//
//        // when then
//        assertThatThrownBy(() -> memberService.createMember(member)).isInstanceOf(O3Exception.class);
//    }
//
//    @Test
//    @DisplayName("JWT 토큰 정보로 저장되지 않은 회원 조회시 예외")
//    void throwException_whenSearchMemberWithJWT() {
//        // when then
//        assertThatThrownBy(() -> memberService.searchDetailMember(member.getLoginId())).isInstanceOf(O3Exception.class);
//    }
//
//    @Test
//    @DisplayName("회원 생성 테스트")
//    void success_whenCorrectMember() {
//        // when
//        memberService.createMember(member);
//
//        // then
//        assertThat(memberRepository.findByLoginId(member.getLoginId())).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("회원 주민등록번호 저장시 암호화 테스트")
//    void success_whenMemberRegNoEncrypted() {
//        // when
//        memberService.createMember(member);
//        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
//        String regNo = "";
//        if (findMember.isPresent()) {
//            regNo = AESUtil.decrypt(findMember.get().getRegNo());
//        }
//
//        //then
//        assertThat(regNo.matches("\\d{6}-\\d{7}")).isTrue();
//    }
//
//    @Test
//    @DisplayName("JWT 토큰 정보로 회원 조회")
//    void success_whenSearchMemberWithJWT() {
//        // given
//        memberRepository.save(member);
//
//        // when then
//        assertThat(memberService.searchDetailMember(member.getLoginId())).isNotNull();
//    }
//
//}