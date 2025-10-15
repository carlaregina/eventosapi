package com.eventosapi.evento.infra.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventosapi.evento.infra.clients.UsuarioApiClient;
import com.eventosapi.evento.infra.dtos.UsuarioDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtValidator jwtBuilder;
    private final UsuarioApiClient usuarioApiClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = this.recoverToken(request);
        Optional<UsuarioDTO> user = this.recoverTokenOwner(token);
        if(user.isPresent()) {
            var authentication = new UsernamePasswordAuthenticationToken(user,  token, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> recoverToken(HttpServletRequest request){
        var authorization = request.getHeader("Authorization");
        if(authorization == null) return Optional.empty();
        return Optional.of(authorization.replace("Bearer ", ""));
    }

    private Optional<UsuarioDTO> recoverTokenOwner(Optional<String> token) {
        try {
            if(token.isPresent()){
                String subject = jwtBuilder.validate(token.get());
                return usuarioApiClient.buscarPorEmail(token.get(), subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}