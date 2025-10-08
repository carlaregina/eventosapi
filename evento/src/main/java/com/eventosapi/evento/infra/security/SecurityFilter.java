package com.eventosapi.evento.infra.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventosapi.evento.infra.entity.UsuarioEntity;
import com.eventosapi.evento.infra.repository.UserJpaRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtValidator jwtBuilder;
    private final UserJpaRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = this.recoverToken(request);
        Optional<UsuarioEntity> user = this.recoverTokenOwner(token);
        if(user.isPresent()) {
            var authentication = new UsernamePasswordAuthenticationToken(user,  null, user.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> recoverToken(HttpServletRequest request){
        var authorization = request.getHeader("Authorization");
        if(authorization == null) return Optional.empty();
        return Optional.of(authorization.replace("Bearer ", ""));
    }

    private Optional<UsuarioEntity> recoverTokenOwner(Optional<String> token) {
        if(token.isPresent()){
            String subject = jwtBuilder.validate(token.get());
            return userRepository.findByEmail(subject);
        }
        return Optional.empty();
    }
}
