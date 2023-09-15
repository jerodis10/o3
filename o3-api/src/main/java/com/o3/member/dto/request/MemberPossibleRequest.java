package com.o3.member.dto.request;

import com.o3.member.domain.MemberPossible;
import com.o3.security.common.AESUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MemberPossibleRequest {

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, max = 6, message = "이름은 2글자 이상, 6글자 이하로 입력해주세요.")
    private final String name;

    @NotBlank(message = "주민번호를 입력해주세요")
    private final String regNo;

    public MemberPossible toEntity() {
        MemberPossible memberPossible = MemberPossible.builder()
                .name(name)
                .regNo(regNo)
                .build();

        memberPossible.regNoEncode(AESUtil.encrypt(regNo));
        return memberPossible;
    }

    public static List<MemberPossible> makeList(List<MemberPossibleRequest> requestList) {
        List<MemberPossible> list = new ArrayList<>();
        for (MemberPossibleRequest request : requestList) {
            list.add(request.toEntity());
        }
        return list;
    }

}
