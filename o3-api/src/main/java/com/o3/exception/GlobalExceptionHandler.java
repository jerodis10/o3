package com.o3.exception;

import com.o3.response.CustomResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(O3Exception.class)
    public CustomResponse<Void> handleStationException(O3Exception e) {
        log.error("handleStationException : {}", e.getMessage());
        return CustomResponse.error(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public CustomResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("handleIllegalArgumentException : {}", e.getMessage());
        return CustomResponse.error(CommonExceptionStatus.WRONG_ARGUMENT.getCode(), CommonExceptionStatus.WRONG_ARGUMENT.getMessage());
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.warn("handleIllegalArgument : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.error(CommonExceptionStatus.METHOD_ARGUMENT_NOT_VALID.getCode(), CommonExceptionStatus.METHOD_ARGUMENT_NOT_VALID.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public CustomResponse<Void> handleFeignException(FeignException e) {
        log.error("handleFeignException : {}", e.getMessage());
        return CustomResponse.error(CommonExceptionStatus.INTERNAL_SERVER_ERROR.getCode(), CommonExceptionStatus.INTERNAL_SERVER_ERROR.getMessage());
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public CustomResponse<Void> handleCallNotPermittedException(CallNotPermittedException e) {
        log.error("handleFeignException : {}", e.getMessage());
        return CustomResponse.error(CommonExceptionStatus.INTERNAL_SERVER_ERROR.getCode(), CommonExceptionStatus.INTERNAL_SERVER_ERROR.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CustomResponse<Void> handleAllException(Exception e) {
        log.warn("handleAllException : {}", e.getMessage());

        return CustomResponse.error(CommonExceptionStatus.INTERNAL_SERVER_ERROR.getCode(), CommonExceptionStatus.INTERNAL_SERVER_ERROR.getMessage());
    }

}
