package com.devteria.identity.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (servletRequestAttributes == null) {
            return;
        }

        String authHeader = servletRequestAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Authorization header: {}", authHeader);

        if (StringUtils.hasText(authHeader)) {
            template.header(HttpHeaders.AUTHORIZATION, authHeader);
        }
    }
}
