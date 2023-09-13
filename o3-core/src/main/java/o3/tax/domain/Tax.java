package o3.tax.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o3.entity.BaseEntity;
import o3.member.domain.Member;
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

//    @Column(nullable = false)
//    @Audited(withModifiedFlag = true)
//    private String name;
//
//    @Column(nullable = false)
//    @Audited(withModifiedFlag = true)
//    private String regNo;

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
//        this.name = name;
//        this.regNo = regNo;
        this.taxAmount = taxAmount;
        this.retirementPension = retirementPension;
        this.insurance = insurance;
        this.medicalExpenses = medicalExpenses;
        this.educationExpenses = educationExpenses;
        this.donation = donation;
        this.totalPaymentAmount = totalPaymentAmount;
    }

}
