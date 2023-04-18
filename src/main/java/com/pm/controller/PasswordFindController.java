/*
 * package com.pm.controller;
 * 
 * 
 * 
 * import java.util.Random;
 * 
 * import javax.mail.internet.MimeMessage;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.mail.javamail.MimeMessageHelper; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.servlet.ModelAndView;
 * 
 * @Controller public class PasswordFindController {
 * 
 * @Autowired JavaMailSender mailSender;
 * 
 * @PostMapping("/pw-find") public ModelAndView
 * sendRandomNumberToEmail(@RequestParam("email") String email) throws Exception
 * { ModelAndView mav = new ModelAndView(); Random random = new Random(); int
 * randomNumber = random.nextInt(10000); // 0부터 9999까지의 랜덤 숫자 생성
 * 
 * String from = "1120ksh98@gmail.com"; // 보내는 사람 이메일 주소 String to = email; //
 * 받는 사람 이메일 주소
 * 
 * MimeMessage message = mailSender.createMimeMessage(); MimeMessageHelper
 * helper = new MimeMessageHelper(message, true, "UTF-8"); helper.setFrom(from);
 * helper.setTo(to); helper.setSubject("비밀번호 찾기 인증번호"); helper.setText("인증번호 : "
 * + randomNumber);
 * 
 * mailSender.send(message);
 * 
 * mav.addObject("email", email); mav.setViewName("send-random-number"); return
 * mav; } }
 */