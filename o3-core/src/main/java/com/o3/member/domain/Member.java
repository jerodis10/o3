package com.o3.member.domain;

import com.o3.entity.BaseEntity;
import com.o3.tax.domain.Tax;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
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
    List<Tax> taxList;

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

    public void addTax(Tax tax) {
        this.taxList.add(tax);
        tax.updateMember(this);
    }

    public void passwordEncode(String encode) {
        this.password = encode;
    }

    public void regNoEncode(String encode) {
        this.regNo = encode;
    }
}
