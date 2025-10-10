package com.eventosapi.inscricao.application.dtos;

import java.util.Map;

public class EmailDTO {
    private String to;
    private String subject;
    private String body;
    private Map<String, byte[]> attachments;

    public EmailDTO(String to, String subject, String body, Map<String, byte[]> attachments) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachments = attachments;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }    

    public Map<String, byte[]> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, byte[]> attachments) {
        this.attachments = attachments;
    }
}
