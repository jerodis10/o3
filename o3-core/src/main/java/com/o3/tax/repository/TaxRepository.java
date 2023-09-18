package com.o3.tax.repository;

import com.o3.tax.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface TaxRepository extends JpaRepository<Tax, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Tax> findByMemberId(Long memberId);
}
