package com.o3.exception;

public class O3Exception extends CommonException {

    public O3Exception(O3ExceptionStatus o3ExceptionStatus) {
        super(o3ExceptionStatus.getMessage(), o3ExceptionStatus.getStatusCode());
    }
}
