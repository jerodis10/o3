package o3.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import o3.entity.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
//@Table(name = "member", indexes = { @Index(name = "idx_member", columnList = "member_id") })
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited(withModifiedFlag = true)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String password;

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String name;

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String regNo;

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<Tax> taxList = new ArrayList<>();

    public void addTax(Tax tax) {
        this.taxList.add(tax);
        tax.updateMember(this);
    }

    @Builder
    public Member(String loginId, String name, String password, String role, String regNo) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.regNo = regNo;
    }

    @Builder
    public Member(String name, String regNo) {
        this.name = name;
        this.regNo = regNo;
    }

//    public void addRoom(Room room) {
//        this.rooms.add(room);
//        room.updateMember(this);
//    }

    public void passwordEncode(String encode) {
        this.password = encode;
    }

    public void regNoEncode(String encode) {
        this.regNo = encode;
    }
}
