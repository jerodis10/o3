package com.o3.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.o3.entity.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member_possible")
public class MemberPossible extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited(withModifiedFlag = true)
    @Column(name = "member_possible_id")
    private Long id;

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String name;

    @Column(nullable = false)
    @Audited(withModifiedFlag = true)
    private String regNo;

    @Builder
    public MemberPossible(String name, String regNo) {
        this.name = name;
        this.regNo = regNo;
    }

    public void regNoEncode(String encode) {
        this.regNo = encode;
    }
}
