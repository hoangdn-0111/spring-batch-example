package org.example.springbatch.interceptor;

import org.example.springbatch.config.AuthConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Component
public class BasicAuthInterceptor implements HandlerInterceptor {

    private final AuthConfig authConfig;

    public BasicAuthInterceptor(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            // Decode Basic Auth credentials
            String base64Credentials = authHeader.substring(6);
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);

            // Kiểm tra username và password
            String clientId = values[0];
            String clientSecret = values[1];

            if (authConfig.getClientId().equals(clientId) && authConfig.getClientSecret().equals(clientSecret)) {
                return true;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized");
        return false;
    }
}
