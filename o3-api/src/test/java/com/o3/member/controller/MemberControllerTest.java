package com.o3.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o3.member.dto.request.LoginRequest;
import com.o3.member.dto.request.MemberRequest;
import com.o3.member.dto.response.MemberResponse;
import com.o3.member.service.MemberService;
import com.o3.security.jwt.JwtProvider;
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
import org.springframework.test.web.servlet.ResultActions;

import static com.o3.member.constants.MemberConstants.AUTHORIZATION_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원 가입 - 파라미터 전달 x")
    @WithMockUser
    void createMemberTestWithNoParameter() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 - 잘못된 파라미터(아이디)")
    @WithMockUser
    void createMemberTestWithInvalidLoginId() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("aa", "aaaaa11111", "홍길동", "860824-1655068");

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest))
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 - 잘못된 파라미터(패스워드)")
    @WithMockUser
    void createMemberTestWithInvalidPassword() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("aaaaa", "a", "홍길동", "860824-1655068");

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest))
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입 - 잘못된 파라미터(주민등록번호)")
    @WithMockUser
    void createMemberTestWithInvalidRegNo() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("aaaaa", "aaaaa1111", "홍길동", "8608");

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest))
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입")
    @WithMockUser
    void createMemberTest() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("aaaaa", "aaaaa11111", "홍길동", "860824-1655068");

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest))
        );

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("로그인 - 파라미터 전달 x")
    @WithMockUser
    void loginMemberTestWithNoParameter() throws Exception {
        // given

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 - 잘못된 파라미터(아이디)")
    @WithMockUser
    void loginMemberTestWithInvalidLoginId() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("a", "aaaaaaa11111");

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 - 잘못된 파라미터(패스워드)")
    @WithMockUser
    void loginMemberTestWithInvalidPassword() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("aaaaa", "a");

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인")
    @WithMockUser
    void loginMemberTest() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("aaaaa", "aaaaaaa11111");

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/szs/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        );

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("가입한 회원 정보 조회 - 인증식별값 헤더에 없음")
    @WithMockUser
    void searchDetailMemberTestWithNoAuthHeader() throws Exception {
        // given
        given(memberService.searchDetailMember(any())).willReturn(null);

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
    void searchDetailMemberTestWithNoMember() throws Exception {
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
    void searchDetailMemberTest() throws Exception {
        // given
        MemberResponse memberResponse = MemberResponse.builder()
                .loginId("a")
                .name("홍길동")
                .regNo("860824-1655068")
                .role("MEMBER")
                .build();

        given(memberService.searchDetailMember(any())).willReturn(memberResponse);

        // when
        final ResultActions resultActions = mockMvc.perform(get("/szs/me")
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().json(objectMapper.writeValueAsString(memberResponse)));
    }

    @Test
    @DisplayName("인증되지 않은 유저의 가입한 회원 정보 조회")
    void searchDetailMemberTestWithUnauthenticated() throws Exception {
        // given
        given(memberService.searchDetailMember(any())).willReturn(null);

        // when
        final ResultActions resultActions = mockMvc.perform(get("/szs/me")
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isUnauthorized());
    }
}