package o3.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o3.entity.BaseEntity;
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

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String name;

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String regNo;

    @Audited(withModifiedFlag = true)
    private String taxAmount;

    @Audited(withModifiedFlag = true)
    private String retirementPension;

    @Audited(withModifiedFlag = true)
    private String insurance;

    @Audited(withModifiedFlag = true)
    private String medicalExpenses;

    @Audited(withModifiedFlag = true)
    private String educationExpenses;

    @Audited(withModifiedFlag = true)
    private String donation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateMember(Member member) {
        this.member = member;
    }

    @Builder
    public Tax(String name, String regNo, String taxAmount, String retirementPension, String insurance, String medicalExpenses, String educationExpenses, String donation) {
        this.name = name;
        this.regNo = regNo;
        this.taxAmount = taxAmount;
        this.retirementPension = retirementPension;
        this.insurance = insurance;
        this.medicalExpenses = medicalExpenses;
        this.educationExpenses = educationExpenses;
        this.donation = donation;
    }


    //    @Builder
//    public Tax(String loginId, String name, String password, String role, String regNo) {
//        this.loginId = loginId;
//        this.name = name;
//        this.password = password;
//        this.role = role;
//        this.regNo = regNo;
//    }

//    @Builder
//    public Tax(String name, String regNo) {
//        this.name = name;
//        this.regNo = regNo;
//    }

//    public void addRoom(Room room) {
//        this.rooms.add(room);
//        room.updateMember(this);
//    }

//    public void passwordEncode(String encode) {
//        this.password = encode;
//    }
//
//    public void regNoEncode(String encode) {
//        this.regNo = encode;
//    }
}
