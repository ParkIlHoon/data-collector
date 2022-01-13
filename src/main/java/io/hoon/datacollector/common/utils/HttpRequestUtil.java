package io.hoon.datacollector.common.utils;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpRequestUtil {

    private static final String[] REMOTE_IP_HEADERS = { "X-Forwarded-For" , "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED",
                                                        "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};

    /**
     * 클라이언트의 IP를 반환합니다.
     *
     * @return {@link #REMOTE_IP_HEADERS} 헤더가 존재할 경우 첫번째 IP
     */
    public static String getRemoteIp() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        for (String header : REMOTE_IP_HEADERS) {
            String requestHeader = request.getHeader(header);
            if (StringUtils.hasText(requestHeader)) {
                return requestHeader.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 요청의 Locale 정보를 반환합니다.
     *
     * @return Accept-Language 헤더를 통해 클라이언트로부터 전달받은 Locale 정보
     */
    public static Locale getLocale() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return Locale.getDefault();
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest().getLocale();
    }

    private HttpRequestUtil() {
        throw new IllegalStateException();
    }
}
