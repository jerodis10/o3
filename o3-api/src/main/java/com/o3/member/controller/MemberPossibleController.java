package com.o3.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import com.o3.member.dto.request.MemberPossibleRequest;
import com.o3.member.service.MemberPossibleService;
import com.o3.response.CustomResponse;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "회원 가입", description = "회원가입 가능한 유저만 회원가입", tags = { "MemberPossible Controller" })
    @PostMapping("/member")
    public ResponseEntity<Void> loginMember(@Validated @RequestBody List<MemberPossibleRequest> memberList) {
        memberPossibleService.createPossibleMember(MemberPossibleRequest.makeList(memberList));
        return ResponseEntity.noContent().build();
    }

}
