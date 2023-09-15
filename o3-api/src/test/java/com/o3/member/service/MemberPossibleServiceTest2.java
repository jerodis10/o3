//package com.o3.member.service;
//
//import com.o3.member.domain.MemberPossible;
//import com.o3.member.repository.MemberPossibleRepository;
//import com.o3.exception.O3Exception;
//import com.o3.security.common.AESUtil;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//@SpringBootTest
//@Transactional
//class MemberPossibleServiceTest2 {
//
//    @Autowired
//    private MemberPossibleRepository memberPossibleRepository;
//
//    @Autowired
//    private MemberPossibleService memberPossibleService;
//
//    List<MemberPossible> memberList;
//    MemberPossible memberPossible;
//
//    @BeforeEach
//    void setUp() {
//        memberPossible = MemberPossible.builder()
//                .name("홍길동")
//                .regNo(AESUtil.encrypt("860824-1655068"))
//                .build();
//        memberList = List.of(memberPossible);
//    }
//
//    @Test
//    @DisplayName("회원가입 가능 회원 중복 생성시 예외 발생")
//    void throwException_whenCreateDuplicatePossibleMember() {
//        // given
//        memberPossibleRepository.save(memberPossible);
//        List<MemberPossible> memberList = List.of(memberPossible);
//
//        // when then
//        assertThatThrownBy(() -> memberPossibleService.createPossibleMember(memberList)).isInstanceOf(O3Exception.class);
//    }
//
//    @Test
//    @DisplayName("회원가입 가능 회원 생성 테스트")
//    void success_whenCorrectPossibleMember() {
//        // when
//        memberPossibleService.createPossibleMember(memberList);
//
//        // then
//        Assertions.assertThat(memberPossibleRepository.findByNameAndRegNo(memberPossible.getName(), memberPossible.getRegNo())).isNotEmpty();
//    }
//
//    @Test
//    @DisplayName("회원가입 가능 회원 주민등록번호 저장시 암호화 테스트")
//    void success_whenPossibleMemberRegNoEncrypted() {
//        // when
//        memberPossibleService.createPossibleMember(memberList);
//        Optional<MemberPossible> member = memberPossibleRepository.findByNameAndRegNo(memberPossible.getName(), memberPossible.getRegNo());
//        String regNo = "";
//        if (member.isPresent()) {
//            regNo = AESUtil.decrypt(member.get().getRegNo());
//        }
//
//        //then
//        assertThat(regNo.matches("\\d{6}-\\d{7}")).isTrue();
//    }
//}