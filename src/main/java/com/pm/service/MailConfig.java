package com.pm.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan(basePackages = {"com.pm.service"})
public class MailConfig {
	@Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

	  @Bean
	    public JavaMailSender javaMailSender() {
	        JavaMailSenderImpl javamailSender = new JavaMailSenderImpl();
	        javamailSender.setHost("smtp.gmail.com");
	        javamailSender.setPort(587);
	        javamailSender.setUsername("1120ksh98@gmail.com");
	        javamailSender.setPassword("zusxyrniisrvbtbj");
	        
	        Properties props = javamailSender.getJavaMailProperties();
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.debug", "true");
	        
	        return javamailSender;
	        
	        
	    }
}
