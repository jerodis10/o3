package com.o3.tax.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o3.security.jwt.JwtProvider;
import com.o3.tax.dto.response.TaxRefundResponse;
import com.o3.tax.service.TaxService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TaxController.class)
class TaxControllerTest {

    @MockBean
    private TaxService taxService;

    @MockBean
    private JwtProvider jwtProvider;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("인증되지 않은 유저의 환급액 계산")
    void refundTaxTestWithUnauth() throws Exception {
        // given
        TaxRefundResponse taxRefundResponse = TaxRefundResponse.builder()
                .name("a")
                .determinedTaxAmount("100,000")
                .retirementPensionTaxCredit("200,000")
                .build();

        given(taxService.refundTax(any())).willReturn(taxRefundResponse);

        // when then
        final ResultActions resultActions = mockMvc.perform(get("/szs/refund")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("환급액 계산 - jwt x")
    @WithMockUser
    void refundTaxTestWithNoJWT() throws Exception {
        // given
        TaxRefundResponse taxRefundResponse = TaxRefundResponse.builder()
                .name("a")
                .determinedTaxAmount("100,000")
                .retirementPensionTaxCredit("200,000")
                .build();

        given(taxService.refundTax(any())).willReturn(taxRefundResponse);

        // when
        final ResultActions resultActions = mockMvc.perform(get("/szs/refund")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("환급액 계산")
    @WithMockUser
    void refundTaxTest() throws Exception {
        // given
        TaxRefundResponse taxRefundResponse = TaxRefundResponse.builder()
                .name("a")
                .determinedTaxAmount("100,000")
                .retirementPensionTaxCredit("200,000")
                .build();

        given(taxService.refundTax(any())).willReturn(taxRefundResponse);

        // when
        final ResultActions resultActions = mockMvc.perform(get("/szs/refund")
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().json(objectMapper.writeValueAsString(taxRefundResponse)));
    }

    @Test
    @DisplayName("환급액 정보 스크랩 - Unauth")
    void scrapTaxTestWithUnauth() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(post("/szs/scrap")
                .with(csrf())
                .header(AUTHORIZATION_HEADER, "a")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("환급액 정보 스크랩 - jwt x")
    void scrapTaxTestWithNoJwt() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(post("/szs/scrap")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("환급액 정보 스크랩")
    @WithMockUser
    void scrapTaxTest() throws Exception {
        // when
        final ResultActions resultActions = mockMvc.perform(post("/szs/scrap")
                        .with(csrf())
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNoContent());
    }

}