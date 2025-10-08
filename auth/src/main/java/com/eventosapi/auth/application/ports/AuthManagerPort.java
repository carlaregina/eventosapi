package com.eventosapi.auth.application.ports;

import com.eventosapi.auth.domain.models.Conta;
import com.eventosapi.auth.domain.models.Usuario;

public interface AuthManagerPort {
    Usuario autenticar(Conta conta);
}
