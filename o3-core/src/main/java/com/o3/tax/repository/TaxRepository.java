package com.o3.tax.repository;

import com.o3.tax.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxRepository extends JpaRepository<Tax, Long> {
    Optional<Tax> findByMemberId(Long memberId);
}
