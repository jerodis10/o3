package com.o3.member.repository;

import com.o3.member.domain.MemberPossible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberPossibleRepository extends JpaRepository<MemberPossible, Long> {

    Optional<MemberPossible> findByNameAndRegNo(String name, String regNo);
}
