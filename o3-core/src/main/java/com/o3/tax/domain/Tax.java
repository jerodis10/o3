package com.o3.tax.domain;

import com.o3.entity.BaseEntity;
import com.o3.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

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
    private long taxAmount;

    @Audited(withModifiedFlag = true)
    private long retirementPension;

    @Audited(withModifiedFlag = true)
    private long insurance;

    @Audited(withModifiedFlag = true)
    private long medicalExpenses;

    @Audited(withModifiedFlag = true)
    private long educationExpenses;

    @Audited(withModifiedFlag = true)
    private long donation;

    @Audited(withModifiedFlag = true)
    private long totalPaymentAmount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateMember(Member member) {
        this.member = member;
    }

    @Builder
    public Tax(long taxAmount, long retirementPension, long insurance, long medicalExpenses, long educationExpenses, long donation, long totalPaymentAmount) {
        this.taxAmount = taxAmount;
        this.retirementPension = retirementPension;
        this.insurance = insurance;
        this.medicalExpenses = medicalExpenses;
        this.educationExpenses = educationExpenses;
        this.donation = donation;
        this.totalPaymentAmount = totalPaymentAmount;
    }

}
