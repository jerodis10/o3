package com.o3.tax.controller;

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

import static com.o3.member.constants.MemberConstants.AUTHORIZATION_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


    @Test
    @DisplayName("결정세액과 퇴직연금세액 공제금 계산")
    @WithMockUser
    void createMemberTest() throws Exception {
        // given
        TaxRefundResponse taxRefundResponse = TaxRefundResponse.builder()
                .name("a")
                .determinedTaxAmount("100,000")
                .retirementPensionTaxCredit("200,000")
                .build();

        given(taxService.refundTax(any())).willReturn(taxRefundResponse);

        // when then
        mockMvc.perform(get("/szs/refund")
                        .header(AUTHORIZATION_HEADER, "a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{\"이름\":\"a\",\"결정세액\":\"100,000\",\"퇴직연금세액공제\":\"200,000\"}"
                ))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("인증되지 않은 유저의 결정세액과 퇴직연금세액 공제금 계산")
    void unauthenticatedCreateMemberTest() throws Exception {
        // given
        TaxRefundResponse taxRefundResponse = TaxRefundResponse.builder()
                .name("a")
                .determinedTaxAmount("100,000")
                .retirementPensionTaxCredit("200,000")
                .build();

        given(taxService.refundTax(any())).willReturn(taxRefundResponse);

        // when then
        mockMvc.perform(get("/szs/refund")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

}