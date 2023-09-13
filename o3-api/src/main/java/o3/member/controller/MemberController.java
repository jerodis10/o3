package o3.member.controller;

import lombok.RequiredArgsConstructor;
import o3.member.dto.request.LoginRequest;
import o3.member.dto.request.MemberRequest;
import o3.member.service.MemberService;
import o3.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@Validated @RequestBody MemberRequest memberRequest) {
        return ResponseEntity.ok(memberService.createMember(memberRequest.toEntity()));
    }

    @PostMapping("/login")
    public CustomResponse<Void> loginMember(@Validated @RequestBody LoginRequest loginRequest) {
        return CustomResponse.empty();
//        return ResponseEntity.ok(memberService.login(loginRequest));
    }

    @PostMapping("/me")
    public ResponseEntity<?> searchDetailMember(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.searchDetailMember(request));
    }


}
