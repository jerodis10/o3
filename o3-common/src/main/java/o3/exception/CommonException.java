package o3.exception;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public CommonException(final String errorMessage, final String errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(final String errorMessage, final String errorCode, final String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonException(CommonExceptionStatus commonExceptionStatus) {
        super(commonExceptionStatus.getMessage());
        this.errorCode = commonExceptionStatus.getCode();
        this.errorMessage = commonExceptionStatus.getMessage();
    }

    public CommonException(CommonExceptionStatus commonExceptionStatus, final String detailMessage) {
        super(detailMessage);
        this.errorCode = commonExceptionStatus.getCode();
        this.errorMessage = commonExceptionStatus.getMessage();
    }
}
