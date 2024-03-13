package com.wotemo.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void SendEmail(String to, String subject, String text) throws MessagingException;
}
