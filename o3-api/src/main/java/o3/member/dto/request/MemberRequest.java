package o3.member.dto.request;

import lombok.Getter;
import o3.member.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class MemberRequest {


    @NotBlank(message = "아이디를 입력해주세요")
//    @Size(min = 4, max = 12, message = "아이디는 4글자 이상, 12글자 이하로 입력해주세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Size(min = 8, max = 20, message = "비밀번호는 8글자 이상, 20글자 이하로 입력해주세요.")
//    @Pattern (regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8.20}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8 ~20자의 비밀번호여야 합니다.")
    @NotNull
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, max = 6, message = "이름은 2글자 이상, 6글자 이하로 입력해주세요.")
    private String name;

    private String role = "MEMBER";

    @NotBlank(message = "주민번호를 입력해주세요")
    @Size(min = 14, max = 14, message = " 주민등록번호는 -을 포함하여 14글자를 입력해주세요.")
    @Pattern(regexp = "\\d{2}([0]\\d|[1][0-2])([0][1-9]|[1-2]\\d|[3][0-1])[-]*[1-4]\\d{6}")
    public String regNo;

    public Member toEntity() {
        return Member.builder()
                .loginId(loginId)
                .name(name)
                .password(password)
                .role(role)
                .regNo(regNo)
                .build();
    }

}
