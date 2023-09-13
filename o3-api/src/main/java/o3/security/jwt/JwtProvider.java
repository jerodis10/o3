package o3.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import o3.security.config.JwtConfig;
import o3.security.sec.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    // access token 유효시간
    private final long accessTokenValidTime = 2 * 60 * 60 * 1000L;

    /**
     * 토큰에서 Claim 추출
     */
    private Claims getClaimsFormToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰에서 인증 subject 추출
     */
    private String getSubject(String token) {
        return getClaimsFormToken(token).getSubject();
    }

    /**
     * 토큰에서 인증 정보 추출
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰 발급
     */
    public String generateJwtToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(authentication.getPrincipal()));
        claims.put("roles", authentication.getAuthorities());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 토큰 검증
     */
    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            return !claims.getExpiration().before(new Date());

        } catch (MalformedJwtException malformedJwtException) {
            log.error("Invalid JWT token");
            throw malformedJwtException;
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Expired JWT token");
            throw expiredJwtException;
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.error("Unsupported JWT token");
            throw unsupportedJwtException;
        } catch (JwtException jwtException) {
            log.error("Invalid JWT token");
            throw jwtException;
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("JWT claims string is empty.");
            throw illegalArgumentException;
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * Request Header에서 토큰 추출
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken)) {
            String[] token = bearerToken.split(" ");
            if(token.length > 1) return token[1];
//            return bearerToken;
        }
        return null;
    }

    /**
     * jwt 토큰에서 userId 검색
     */
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFormToken(token);
        return claimsResolver.apply(claims);
    }

}
