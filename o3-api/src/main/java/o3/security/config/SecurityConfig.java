package o3.security.config;

import lombok.RequiredArgsConstructor;
import o3.security.jwt.JwtFilter;
import o3.security.jwt.JwtProvider;
import o3.security.sec.AuthenticationProviderImpl;
import o3.security.sec.CustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // JWT 제공 클래스
    private final JwtProvider jwtProvider;
    // 인증 실패 또는 인증헤더가 전달받지 못했을때 핸들러
    private final AuthenticationEntryPoint authenticationEntryPoint;
    // 인증 성공 핸들러
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    // 인증 실패 핸들러
    private final AuthenticationFailureHandler authenticationFailureHandler;
    // 인가 실패 핸들러
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    @Order(0)
    public SecurityFilterChain resources(HttpSecurity http) throws Exception {
        return http.requestMatchers(matchers -> matchers
                        .antMatchers("/resources/**"))
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .requestCache(RequestCacheConfigurer::disable)
                .securityContext(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                .and()
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                .authorizeRequests(authorize -> authorize
                        .antMatchers("/szs/login", "/szs/signup", "/h2-console").permitAll()
                        .antMatchers("/admin").hasRole("ADMIN")
                        .antMatchers("/my").authenticated())
//                        .anyRequest().authenticated())

                .formLogin().disable()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
    }

    @Bean
    public AuthenticationProviderImpl customAuthenticationProvider() {
        return new AuthenticationProviderImpl(passwordEncoder());
    }


    /**
     * 비밀번호 암호화 및 확인 클래스
     */

//    @Bean
//    public AesBytesEncryptor aesBytesEncryptor() {
//        return new AesBytesEncryptor();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new StandardPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 사용자 요청 정보로 UserPasswordAuthenticationToken 발급하는 필터
     */
    @Bean
    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        // 필터 URL 설정
        customAuthenticationFilter.setFilterProcessesUrl("/szs/login");
        // 인증 성공 핸들러
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        // 인증 실패 핸들러
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        // BeanFactory에 의해 모든 property가 설정되고 난 뒤 실행
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    /**
     * JWT의 인증 및 권한을 확인하는 필터
     */
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider);
    }

}