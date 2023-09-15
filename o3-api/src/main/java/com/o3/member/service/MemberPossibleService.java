package com.o3.member.service;

import com.o3.exception.O3Exception;
import com.o3.exception.O3ExceptionStatus;
import com.o3.member.domain.MemberPossible;
import com.o3.member.repository.MemberPossibleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberPossibleService {

    private final MemberPossibleRepository memberPossibleRepository;

    @CachePut(value = "member")
    @Transactional
    public void createPossibleMember(List<MemberPossible> memberList) {
        for (MemberPossible member : memberList) {
            memberPossibleRepository.findByNameAndRegNo(member.getName(), member.getRegNo())
                    .ifPresent(m -> { throw new O3Exception(O3ExceptionStatus.DUPLICATION_MEMBER); });

            memberPossibleRepository.save(member);
        }
    }
}
