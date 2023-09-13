package o3.member.util;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import o3.exception.O3Exception;
import o3.exception.O3ExceptionStatus;
import o3.member.domain.Member;
import o3.member.domain.MemberPossible;
import o3.member.repository.MemberRepository;
import o3.security.common.AESUtil;

import java.util.List;

@UtilityClass
public class MemberValidator {

    public void validateCreation(List<MemberPossible> memberList, Member member) {
        validateMember(memberList, member);
    }

    public void validateMember(List<MemberPossible> memberList, Member member) {
        memberList.stream()
                .filter(m -> m.getName().equals(member.getName()) && m.getRegNo().equals(AESUtil.encrypt(member.getRegNo())))
                .findAny()
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.INVALID_MEMBER));
    }

}
