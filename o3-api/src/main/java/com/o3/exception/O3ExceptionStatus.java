package com.o3.exception;

import lombok.Getter;

@Getter
public enum O3ExceptionStatus {
    DUPLICATION_MEMBER("O3_1000", "이미 해당 MEMBER 가 존재합니다."),
    NO_MEMBER("O3_2100", "등록된 MEMBER 가 없습니다."),
    NO_PASSWORD("O3_2200", "해당 MEMBER 에 password 가 없습니다."),
    INVALID_MEMBER("O3_2300", "유효하지 않은 MEMBER 입니다."),
    FAIL_CONNECT_EXTERNAL_API("O3_2400", "외부 API 연동에 실패했습니다."),
    NO_JSON_EXTERNAL_API("O3_2410", "외부 API 연동시 데이터를 가져오지 못했습니다."),
    NO_SALARY_EXTERNAL_API("O3_2420", "외부 API 연동시 급여 리스트를 가져오지 못했습니다."),
    NO_INCOME_EXTERNAL_API("O3_2430", "외부 API 연동시 소득공제 리스트를 가져오지 못했습니다."),
    NO_TAX("O3_2500", "등록된 환급 정보가 없습니다.")
    ;


    private final String statusCode;
    private final String message;

    O3ExceptionStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
