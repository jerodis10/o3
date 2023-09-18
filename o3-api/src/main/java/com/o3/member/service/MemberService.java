package com.o3.member.service;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.domain.Member;
import com.o3.member.domain.MemberPossible;
import com.o3.member.dto.response.MemberResponse;
import com.o3.member.repository.MemberPossibleRepository;
import com.o3.member.repository.MemberRepository;
import com.o3.member.util.MemberValidator;
import com.o3.security.common.AESUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberPossibleRepository memberPossibleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(Member member) {
        List<MemberPossible> memberList = memberPossibleRepository.findAll();
        MemberValidator.validateCreation(memberList, member);

        memberRepository.findByLoginId(member.getLoginId())
                .ifPresent(m -> { throw new O3Exception(O3ExceptionStatus.DUPLICATION_MEMBER); });

        encode(member);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse searchDetailMember(String loginId) {
        return MemberResponse.toDto(memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER)));
    }

    private void encode(Member member) {
        member.passwordEncode(passwordEncoder.encode(member.getPassword()));
        member.regNoEncode(AESUtil.encrypt(member.getRegNo()));
    }
}
