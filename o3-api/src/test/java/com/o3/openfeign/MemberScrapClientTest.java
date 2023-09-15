//package com.o3.openfeign;
//
//import com.o3.tax.dto.request.TaxScrapRequest;
//import com.o3.tax.dto.response.TaxScrapResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class MemberScrapClientTest {
//
//    @Autowired
//    private MemberScrapClient memberScrapClient;
//
//    @Test
//    @DisplayName("MemberScrapClientTest")
//    void MemberScrapClientTest() {
//        // given
//        TaxScrapRequest taxScrapRequest = TaxScrapRequest.builder().name("홍길동").regNo("860824-1655068").build();
//
//        // when
//        TaxScrapResponse result = memberScrapClient.call(taxScrapRequest);
//
//        //then
//        assertThat(result).isNotNull();
//    }
//}