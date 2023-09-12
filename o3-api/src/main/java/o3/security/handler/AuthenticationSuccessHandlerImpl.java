package o3.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import o3.security.common.ApiResponse;
import o3.security.common.ApiResponseType;
import o3.security.jwt.JwtFilter;
import o3.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 전달받은 인증정보 SecurityContextHolder에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT Token 발급
        final String token = jwtProvider.generateJwtToken(authentication);
        // Response
         responseToken(response, token);
    }

    private void responseToken(HttpServletResponse response, String jwtToken) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ApiResponseType.SUCCESS.getCode());

//        Cookie cookie = new Cookie("Authorization", jwtToken);
//        cookie.setPath("/");
//        response.addCookie(cookie);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtToken);

        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwtToken);

//        response.getWriter().write(Objects.requireNonNull(objectMapper.writeValueAsString(new ApiResponse(ApiResponseType.SUCCESS.getCode(), jwtToken))));
        response.getWriter().write(Objects.requireNonNull(objectMapper.writeValueAsString(jwtToken)));
    }

}
