package com.o3.member.controller;

import com.o3.member.dto.request.LoginRequest;
import com.o3.member.dto.request.MemberRequest;
import com.o3.member.dto.response.MemberResponse;
import com.o3.member.service.MemberService;
import com.o3.security.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.o3.member.constants.MemberConstants.AUTHORIZATION_HEADER;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Operation(summary = "회원 가입", description = "회원 가입 요청됩니다.", tags = { "Member Controller" })
    @PostMapping("/signup")
    public ResponseEntity<Void> createMember(@Validated @RequestBody MemberRequest memberRequest) {
        memberService.createMember(memberRequest.toEntity());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "로그인", description = "가입한 회원 로그인", tags = { "Member Controller" })
    @PostMapping("/login")
    public ResponseEntity<Void> loginMember(@Validated @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 정보", description = "가입한 회원 정보 조회", tags = { "Member Controller" })
    @GetMapping("/me")
    public ResponseEntity<MemberResponse> searchDetailMember(@RequestHeader(AUTHORIZATION_HEADER) String jwtToken) {
        return ResponseEntity.ok(memberService.searchDetailMember(jwtProvider.getLoginIdFromToken(jwtToken)));
    }


}
