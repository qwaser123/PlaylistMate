package com.pm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pm.service.SendEmailService;

@Controller
public class UserController {
    
    @Autowired
    private SendEmailService sendemailService; 

    @PostMapping("/send-password")
    @ResponseBody
    public void sendNewPassword(HttpServletRequest request) {
        String email = request.getParameter("email");

        // 새로운 비밀번호 생성하고 이메일 전송
        String newPassword = sendemailService.getTempPassword();
        sendemailService.mailSend(email, newPassword);
    }
}

