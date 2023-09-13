package o3.exception;

import lombok.Getter;

@Getter
public enum O3ExceptionStatus {
    DUPLICATION_MEMBER("STA_1000", "이미 해당 MEMBER 가 존재합니다."),
    NO_MEMBER("STA_2100", "등록된 MEMBER 가 없습니다."),
    NO_PASSWORD("STA_2200", "해당 MEMBER 에 password 가 없습니다."),
    INVALID_MEMBER("STA_2300", "유효하지 않은 MEMBER 입니다."),
    FAIL_CONNECT_EXTERNAL_API("STA_2400", "외부 API 연동에 실패했습니다."),
    NO_JSON_EXTERNAL_API("STA_2410", "외부 API 연동시 jsonList를 가져오지 못했습니다.")
    ;


    private final String statusCode;
    private final String message;

    O3ExceptionStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
