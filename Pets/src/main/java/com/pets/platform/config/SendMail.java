package com.pets.platform.config;

import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SendMail {
	
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(465);
        mailSender.setUsername("yunjiwan72@gmail.com");
        mailSender.setPassword("qbdhtuhkemkgrhee"); // 앱 비밀번호 사용 (환경 변수로 관리 권장)

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true"); // SSL 사용
        props.put("mail.debug", "true"); // 디버깅 로그 활성화

        return mailSender;
    }

}
