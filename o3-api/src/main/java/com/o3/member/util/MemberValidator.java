package com.o3.member.util;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.domain.Member;
import com.o3.member.domain.MemberPossible;
import com.o3.security.common.AESUtil;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MemberValidator {

    public void validateCreation(List<MemberPossible> memberList, Member member) {
        validateMember(memberList, member);
    }

    public void validateMember(List<MemberPossible> memberList, Member member) {
        if (memberList.stream().noneMatch(m -> m.getName().equals(member.getName()) && m.getRegNo().equals(AESUtil.encrypt(member.getRegNo())))) {
            throw new O3Exception(O3ExceptionStatus.INVALID_MEMBER);
        }
    }

}
