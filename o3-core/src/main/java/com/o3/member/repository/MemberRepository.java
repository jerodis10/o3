package com.o3.member.repository;

import com.o3.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByNameAndRegNo(String name, String regNo);

}
