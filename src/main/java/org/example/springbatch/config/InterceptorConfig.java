package org.example.springbatch.config;

import lombok.RequiredArgsConstructor;
import org.example.springbatch.anotation.BasicAuthRequired;
import org.example.springbatch.interceptor.BasicAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    private final BasicAuthInterceptor basicAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if (handler instanceof HandlerMethod handlerMethod &&
                        (handlerMethod.getMethod().isAnnotationPresent(BasicAuthRequired.class) ||
                                handlerMethod.getBeanType().isAnnotationPresent(BasicAuthRequired.class))) {
                    return basicAuthInterceptor.preHandle(request, response, handler);
                }
                return true;
            }
        });
    }
}
