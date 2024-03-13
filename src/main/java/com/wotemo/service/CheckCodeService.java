package com.wotemo.service;

import jakarta.mail.MessagingException;

import java.util.UUID;

public interface CheckCodeService {
    void addCode(String id, String code, long timeout);
    String addRandomCode(String to, long timeout) throws MessagingException;
    String getCode(String id);
    boolean checkCode(String id, String code);

    void deleteCode(String id);
}
