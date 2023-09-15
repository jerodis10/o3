package com.o3.security.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiResponse {

    private int code = ApiResponseType.SUCCESS.getCode();
    private String msg = ApiResponseType.SUCCESS.getMessage();

    public static ApiResponse error(ApiResponseType apiResponseType) {
        return new ApiResponse(apiResponseType.getCode(), apiResponseType.getMessage());
    }

    public static ApiResponse error(ApiResponseType apiResponseType, String Message) {
        return new ApiResponse(apiResponseType.getCode(), Message);
    }

    public static void error(ServletResponse response, ApiResponseType apiResponseType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(apiResponseType.getCode());
        httpServletResponse.getWriter().write(Objects.requireNonNull(objectMapper.writeValueAsString(ApiResponse.error(apiResponseType))));
    }

}
