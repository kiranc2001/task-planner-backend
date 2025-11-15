package com.taskplanner.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailHelper {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendOtpEmail(String to, String otp) {
        sendEmail(to, "Password Reset OTP", "Your OTP for password reset is: " + otp + ". It expires in 10 minutes.");
    }
}
