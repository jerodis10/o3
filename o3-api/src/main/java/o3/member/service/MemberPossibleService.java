package o3.member.service;

import lombok.RequiredArgsConstructor;
import o3.member.domain.MemberPossible;
import o3.member.dto.request.MemberPossibleRequest;
import o3.member.repository.MemberPossibleRepository;
import o3.security.common.AESUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPossibleService {

    private final MemberPossibleRepository memberPossibleRepository;

    public void createPossibleMember(List<MemberPossibleRequest> memberList) {
        for (MemberPossibleRequest member : memberList) {
            MemberPossible memberPossible = MemberPossible.builder()
                    .name(member.getName())
                    .regNo(AESUtil.encrypt(member.getRegNo()))
                    .build();

            memberPossibleRepository.save(memberPossible);
        }
    }
}
