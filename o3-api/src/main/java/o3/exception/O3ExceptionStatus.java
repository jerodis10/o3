package o3.exception;

import lombok.Getter;

@Getter
public enum O3ExceptionStatus {
    DUPLICATION_MEMBER("STA_1000", "이미 해당 MEMBER 가 존재합니다."),
    DUPLICATION_ROOM("STA_1100", "이미 해당 ROOM 이 존재합니다."),
    DUPLICATION_TRAN("STA_1200", "이미 해당 거래유형 이 존재합니다."),
    NO_MEMBER("STA_2100", "등록된 MEMBER 가 없습니다."),
    NO_ROOM("STA_2200", "등록된 ROOM 이 없습니다."),
    NO_PASSWORD("STA_2300", "해당 MEMBER 에 password 가 없습니다."),
    INVALID_MEMBER("STA_2400", "유효하지 않은 MEMBER 입니다."),
    FAIL_EXTERNAL_API("STA_2500", "외부 API 연동에 실패했습니다.")
    ;


    private final String statusCode;
    private final String message;

    O3ExceptionStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
