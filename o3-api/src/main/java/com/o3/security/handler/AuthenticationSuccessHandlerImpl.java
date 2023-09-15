package com.o3.security.handler;

import com.o3.security.common.ApiResponseType;
import com.o3.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.o3.member.constants.MemberConstants.AUTHORIZATION_HEADER;
import static com.o3.member.constants.MemberConstants.BEARER_HEADER;

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
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ApiResponseType.SUCCESS.getCode());
        response.setHeader(AUTHORIZATION_HEADER, BEARER_HEADER + " " + jwtToken);
    }

}
