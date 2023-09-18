package com.o3.tax.domain;

import com.o3.entity.BaseEntity;
import com.o3.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tax")
public class Tax extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private Long id;

    @Audited(withModifiedFlag = true)
    private BigDecimal taxAmount;

    @Audited(withModifiedFlag = true)
    private BigDecimal retirementPension;

    @Audited(withModifiedFlag = true)
    private BigDecimal insurance;

    @Audited(withModifiedFlag = true)
    private BigDecimal medicalExpenses;

    @Audited(withModifiedFlag = true)
    private BigDecimal educationExpenses;

    @Audited(withModifiedFlag = true)
    private BigDecimal donation;

    @Audited(withModifiedFlag = true)
    private BigDecimal totalPaymentAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateMember(Member member) {
        this.member = member;
    }

    @Builder
    public Tax(BigDecimal taxAmount, BigDecimal retirementPension, BigDecimal insurance, BigDecimal medicalExpenses, BigDecimal educationExpenses, BigDecimal donation, BigDecimal totalPaymentAmount) {
        this.taxAmount = taxAmount;
        this.retirementPension = retirementPension;
        this.insurance = insurance;
        this.medicalExpenses = medicalExpenses;
        this.educationExpenses = educationExpenses;
        this.donation = donation;
        this.totalPaymentAmount = totalPaymentAmount;
    }

}
