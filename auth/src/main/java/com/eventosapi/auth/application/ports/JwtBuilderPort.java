package com.eventosapi.auth.application.ports;

import com.eventosapi.auth.domain.models.Usuario;

public interface JwtBuilderPort {    
    String gerarToken(Usuario usuario);
}
