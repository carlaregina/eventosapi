package com.eventosapi.inscricao.application.port;

import com.eventosapi.inscricao.application.dtos.EmailDTO;

public interface MailClientPort {
    void send(EmailDTO email);
}
