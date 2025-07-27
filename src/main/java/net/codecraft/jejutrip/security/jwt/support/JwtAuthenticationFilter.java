package net.codecraft.jejutrip.security.jwt.support;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.codecraft.jejutrip.common.response.ResponseCode;
import net.codecraft.jejutrip.security.jwt.service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static net.codecraft.jejutrip.common.exception.FilterExceptionHandler.setSuccessResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestUri = httpServletRequest.getRequestURI();

        // [수정] 로그아웃 경로인 경우, 필터의 나머지 로직을 건너뛰고 바로 다음으로 넘김
        if (requestUri.equals("/api/auth/logout")) {
            chain.doFilter(request, response);
            return;
        }

        String token = getAccessTokenFromHeader(request);

        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else if (token != null) {
            jwtService.validateRefreshToken((HttpServletRequest) request , (HttpServletResponse) response);
            setSuccessResponse(((HttpServletResponse) response) , ResponseCode.CREATE_ACCESS_TOKEN);
            return;
        }

        chain.doFilter(request, response);
    }

    public String getAccessTokenFromHeader(ServletRequest request) {
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();

        if (cookies != null && cookies.length != 0) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("accessToken")).findFirst().map(Cookie::getValue)
                    .orElse(null);
        }

        return null;
    }
}