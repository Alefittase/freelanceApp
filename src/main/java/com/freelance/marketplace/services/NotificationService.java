package com.freelance.marketplace.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService {
    @Value("${NOTIFICATION_EMAIL:system@freelance.marketplace.com}")
    private String fromEmail;

    public void sendEmailNotification(String toEmail, String subject, String content) {
        String emailTemplate = """
            From: %s
            To: %s
            Subject: %s
            
            %s
            """.formatted(fromEmail, toEmail, subject, content);
        
        log.info("Email Notification:\n{}", emailTemplate);
    }
}