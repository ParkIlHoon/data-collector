package io.hoon.datacollector.common.filter;

import io.hoon.datacollector.common.properties.KeyStore;
import io.hoon.datacollector.common.token.KeyAuthenticationToken;
import io.hoon.datacollector.common.utils.HttpRequestUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeyAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-Secret-Key";
    private final KeyStore keyStore;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (hasKeyHeader(request)) {
            String key = request.getHeader(HEADER_NAME);
            if (keyStore.hasKey(key)) {
                SecurityContextHolder.getContext().setAuthentication(new KeyAuthenticationToken(key, null, List.of(new SimpleGrantedAuthority("ROLE_USE"))));
            } else {
                log.error("유효하지 않은 키입니다. 클라이언트 IP : {}, 키 : {}", HttpRequestUtil.getRemoteIp(), key);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return;
            }
        } else {
            log.error(HEADER_NAME + " 헤더에 값이 없습니다. 클라이언트 IP : {}", HttpRequestUtil.getRemoteIp());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean hasKeyHeader(HttpServletRequest request) {
        return request.getHeader(HEADER_NAME) != null && !request.getHeader(HEADER_NAME).isEmpty() && !request.getHeader(HEADER_NAME).isBlank();
    }
}
