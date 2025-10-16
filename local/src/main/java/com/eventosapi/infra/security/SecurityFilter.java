package com.eventosapi.infra.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader("x-user-id");
        List<SimpleGrantedAuthority> roles = getRoles(request);
        if(userId != null && !roles.isEmpty()) {
            var authentication = new UsernamePasswordAuthenticationToken(userId,  null, roles);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

	private List<SimpleGrantedAuthority> getRoles(HttpServletRequest request) {
        String roles = request.getHeader("x-user-roles");
        if(roles == null) {
            return List.of();
        }
        return Arrays.stream(roles.split(","))
            .map(SimpleGrantedAuthority::new)
            .toList();
	}
}