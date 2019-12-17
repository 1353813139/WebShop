package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMsg(String sendto,String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 邮件发送者
        mailMessage.setFrom("1353813139@qq.com");
        // 邮件接受者
        mailMessage.setTo(sendto);
        // 邮件主题
        mailMessage.setSubject("发货通知. ");
        // 邮件内容
        mailMessage.setText(content);
        // 发送邮件
        mailSender.send(mailMessage);
    }

}