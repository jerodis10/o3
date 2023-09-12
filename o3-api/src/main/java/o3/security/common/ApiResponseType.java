package o3.security.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ApiResponseType {

    SUCCESS(HttpStatus.OK.value(), "Success"),

    UNAUTHORIZED_RESPONSE(HttpStatus.UNAUTHORIZED.value(), "Unauthorized"),
    FORBIDDEN_RESPONSE(HttpStatus.FORBIDDEN.value(), "Forbidden"),
    NOT_FOUND_RESPONSE(HttpStatus.NOT_FOUND.value(), "Not Found"),
    METHOD_NOT_ALLOWED_RESPONSE(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed"),

    NOT_VALID_RESPONSE(HttpStatus.BAD_REQUEST.value(), "Not Valid"),
    NOT_FOUND_DATA_RESPONSE(HttpStatus.BAD_REQUEST.value(), "Not Found Data({ENTITY})"),
    ALREADY_DATA_RESPONSE(HttpStatus.BAD_REQUEST.value(), "Already Data({ENTITY})"),
    PARSE_ERROR_RESPONSE(HttpStatus.BAD_REQUEST.value(), "Parsing Error"),
    NOT_PRIMARY_ERROR_RESPONSE(HttpStatus.BAD_REQUEST.value(), "Not Primary Key Error"),
    SQL_ERROR_RESPONSE(HttpStatus.BAD_REQUEST.value(), "SQL Error"),
    ILLEGAL_ACCESS_RESPONSE(HttpStatus.BAD_REQUEST.value(), "Illegal Access"),

    TO_MANY_REQUESTS_RESPONSE(HttpStatus.TOO_MANY_REQUESTS.value(), "Too Many Requests");


    private final int code;
    private final String message;

}