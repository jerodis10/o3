package com.o3.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.o3.exception.GlobalExceptionHandler;
import com.o3.member.constants.MemberConstants;
import com.o3.member.dto.response.MemberResponse;
import com.o3.member.service.MemberService;
import com.o3.security.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.o3.security.jwt.JwtFilter.AUTHORIZATION_HEADER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController target;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    MockMvc mockMvc;

//    @BeforeEach
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(target)
//                .setControllerAdvice(new GlobalExceptionHandler())
//                .build();
//    }

    @Test
    @DisplayName("가입한 회원 정보 조회 - 인증식별값 헤더에 없음")
    @WithMockUser
    void createMemberTestWithNoAuthHeader() throws Exception {
        // given
        MemberResponse memberResponse = MemberResponse.builder()
                .loginId("a")
                .name("홍길동")
                .regNo("860824-1655068")
                .role("MEMBER")
                .build();

        given(memberService.searchDetailMember(any())).willReturn(memberResponse);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/szs/me")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("가입한 회원 정보 조회 - 해당 회원이 존재하지 않음")
    @WithMockUser
    void createMemberTestWithNoMember() throws Exception {
        // given
        given(memberService.searchDetailMember(any())).willReturn(null);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/szs/me")
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(content().string(""));
    }

    @Test
    @DisplayName("가입한 회원 정보 조회")
    @WithMockUser
    void createMemberTest() throws Exception {
        // given
        MemberResponse memberResponse = MemberResponse.builder()
                .loginId("a")
                .name("홍길동")
                .regNo("860824-1655068")
                .role("MEMBER")
                .build();

        given(memberService.searchDetailMember(any())).willReturn(memberResponse);

        // when then
        mockMvc.perform(get("/szs/me")
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{\"아이디\":\"a\",\"이름\":\"홍길동\",\"주민등록번호\":\"860824-1655068\",\"권한\":\"MEMBER\"}"
                ))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("인증되지 않은 유저의 가입한 회원 정보 조회")
    void unauthenticatedCreateMemberTest() throws Exception {
        // given
        MemberResponse memberResponse = MemberResponse.builder()
                .loginId("a")
                .name("홍길동")
                .regNo("860824-1655068")
                .role("MEMBER")
                .build();

        given(memberService.searchDetailMember(any())).willReturn(memberResponse);

        // when then
        mockMvc.perform(get("/szs/me")
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}