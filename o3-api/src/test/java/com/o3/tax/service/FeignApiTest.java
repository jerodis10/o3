package com.o3.tax.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

//@RestClientTest(value = {TaxService.class})
public class FeignApiTest {

//    @Autowired
//    private TaxService taxService;
//
//    @Autowired
//    private MockRestServiceServer mockServer;


    @Test
    @DisplayName("외부 API 연동")
    void FeignApiTest() {
        // given
//        String apiUrl = "https://codetest.3o3.co.kr/v2/scrap";
//
//        mockServer
//                .expect(requestTo(apiUrl))
//                .andExpect(method(HttpMethod.GET))
//                .andExpect()

        // when

        //then

    }
}
