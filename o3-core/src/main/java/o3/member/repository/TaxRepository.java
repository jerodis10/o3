package o3.member.repository;

import o3.member.domain.Member;
import o3.member.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaxRepository extends JpaRepository<Tax, Long> {

}
