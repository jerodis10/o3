package o3.member.util;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import o3.exception.O3Exception;
import o3.exception.O3ExceptionStatus;
import o3.member.domain.Member;
import o3.member.repository.MemberRepository;

import java.util.List;

@UtilityClass
public class MemberValidator {

    List<Member> members = List.of(new Member("홍길동", "860824-1655068"),
                                   new Member("김둘리", "921108-1582816"),
                                   new Member("마징가", "880601-2455116"),
                                   new Member("베지터", "910411-1656116"),
                                   new Member("손오공", "820326-2715702"));

    public void validateCreation(Member member) {
        validateInvalidMember(member);
    }

    public void validateInvalidMember(Member member) {
        members.stream()
                .filter(m -> m.getName().equals(member.getName()) && m.getRegNo().equals(member.getRegNo()))
                .findAny()
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.INVALID_MEMBER));
    }

}
