package com.o3.security.sec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o3.exception.CommonException;
import com.o3.exception.CommonExceptionStatus;
import com.o3.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        final UsernamePasswordAuthenticationToken authRequest;

        final Member member;
        try {
            // 사용자 요청 정보로 UserPasswordAuthenticationToken 발급
            member = new ObjectMapper().readValue(request.getInputStream(), Member.class);

            authRequest = new UsernamePasswordAuthenticationToken(member.getLoginId(), member.getPassword());
        } catch (IOException e) {
            throw new CommonException(CommonExceptionStatus.UNEXPECTED);
        } catch (AuthenticationException e) {
            throw new CommonException(CommonExceptionStatus.UNEXPECTED);
        }
        setDetails(request, authRequest);

        // AuthenticationManager에게 전달 -> AuthenticationProvider의 인증 메서드 실행
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
