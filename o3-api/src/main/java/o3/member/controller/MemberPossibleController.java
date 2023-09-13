package o3.member.controller;

import lombok.RequiredArgsConstructor;
import o3.member.dto.request.MemberPossibleRequest;
import o3.member.service.MemberPossibleService;
import o3.response.CustomResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class MemberPossibleController {

    private final MemberPossibleService memberPossibleService;

    @PostMapping("/member")
    public CustomResponse<Void> loginMember(@Validated @RequestBody List<MemberPossibleRequest> memberList) {
        memberPossibleService.createPossibleMember(memberList);
        return CustomResponse.empty();
    }

}
