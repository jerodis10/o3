package o3.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final List<String> EXCLUDE_URL = Arrays.asList("/", "/index.html", "/js/main.js", "/favicon.ico", "/member");
    private final JwtProvider jwtProvider;

    /**
     * 토큰 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // Request Header에서 토큰 추출
        String jwt = jwtProvider.resolveToken(request);
        log.debug("Request Header jwt : {}", jwt);
        // Token 유효성 검사
        if (StringUtils.hasText(jwt) && jwtProvider.isValidToken(jwt)) {
            // 토큰으로 인증 정보를 추출
            Authentication authentication = jwtProvider.getAuthentication(jwt);
            // SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 특정 url 필터에서 제외
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getRequestURI()));
    }

}