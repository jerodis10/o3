package o3.tax.repository;

import o3.tax.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, Long> {
    Tax findByMemberId(Long memberId);
}
