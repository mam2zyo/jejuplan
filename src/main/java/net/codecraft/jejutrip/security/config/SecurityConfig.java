package net.codecraft.jejutrip.security.config;

import lombok.RequiredArgsConstructor;
import net.codecraft.jejutrip.account.user.constant.UserRole;
import net.codecraft.jejutrip.common.exception.FilterExceptionHandler;
import net.codecraft.jejutrip.security.jwt.support.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;
//    private final CustomOAuth2UserService oauth2UserService;
//    private final SingleVisitInterceptor singleVisitInterceptor;
//    private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
//    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http    // CSRF, Form Login, HTTP Basic 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 세션 관리 정책을 STATELESS(세션 사용 안함)으로 설정
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // h2 콘솔 iframe 접근 허용을 위한 설정(개발 전용)
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                );

        http    // HTTP 요청 인가 설정
                .authorizeHttpRequests(auth -> auth

                // 모든 GET 요청 허용
                .requestMatchers(HttpMethod.GET, "/**")
                .permitAll()

                // Admin GET 요청은 MANAGER 권한 필요
                .requestMatchers(HttpMethod.GET, "/api/admin/**")
                .hasRole(UserRole.MANAGER.name())

                // Admin 경로는 MANAGER 권한 필요
                .requestMatchers("/api/admin/**")
//                .hasRole(UserRole.MANAGER.name())
                .hasRole(UserRole.USER.name())

                // 특정 POST 엔드포인트 허용
                .requestMatchers(HttpMethod.POST,
                        "/api/auth/signup",
                        "/api/auth/login",
                        "/api/auth/logout",
                        "/api/auth/refresh",
                        "/api/admin/login")
                .permitAll()

                // 나머지 모든 요청은 인증 필요
                .anyRequest().authenticated());

                // OAuth2 로그인 설정
//                http.oauth2Login(oauth2 -> oauth2
//                        .loginPage("/authorization/denied")
//                        .successHandler(oauth2AuthenticationSuccessHandler)
//                        .failureHandler(customAuthenticationFailureHandler)
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(oauth2UserService))
//                                );

                // 필터 체인 설정
                http.addFilterBefore(new FilterExceptionHandler(),
                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(singleVisitInterceptor,
//                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

                return http.build();
    }
}
