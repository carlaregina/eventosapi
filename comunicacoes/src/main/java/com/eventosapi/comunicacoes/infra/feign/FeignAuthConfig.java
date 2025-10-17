package com.eventosapi.comunicacoes.infra.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignAuthConfig {

    @Bean
    public RequestInterceptor relayAuthorizationHeader() {
        return (RequestTemplate template) -> {
            var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) return;
            
            var request = attrs.getRequest();
            String userId = request.getHeader("x-user-id");
            String userRoles = request.getHeader("x-user-roles");
            String authorization = request.getHeader("Authorization");

            if (!StringUtils.hasText(userId) || 
                !StringUtils.hasText(userRoles) || 
                !StringUtils.hasText(authorization)) {
                return;
            }

            template.header("x-user-id", userId);
            template.header("x-user-roles", userRoles);
            template.header("Authorization", authorization);
        };
    }
}

