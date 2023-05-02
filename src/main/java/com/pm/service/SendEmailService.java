
package com.pm.service;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pm.entity.MemberEntity;
import com.pm.repository.MemberRepository;
  
  @Transactional
  @Service
  public class SendEmailService{
  
	  private final MemberRepository memberRepository;
	  private JavaMailSender mailSender; 
	  private static final String from_address = "1120ksh98@gmail.com"; //발신자 주소

  @Autowired
  public SendEmailService(MemberRepository memberRepository, JavaMailSender mailSender) {
      this.memberRepository = memberRepository;
      this.mailSender = mailSender;
  }
  
  //임시 비밀번호로 변경, db업데이트
  public String createMailAndChangePassword(String email){ 
	  MemberEntity updatedMember = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일을 가진 회원이 존재하지 않습니다."));  
	  String password = getTempPassword(); //getTempPassword()로 생성된 임시비밀번호
	   
	    updatedMember.setPw(password);
	    memberRepository.save(updatedMember);
	    return password;
	}

  //랜덤 임시 비밀번호 
  public String getTempPassword(){ char[] charSet = new char[] { '0', '1', '2',
  '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
  'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
  'X', 'Y', 'Z' };
  
  String str = "";
  
  int idx = 0; for (int i = 0; i < 10; i++) { 
	  idx = (int) (charSet.length *Math.random()); str += charSet[idx]; } 
  	  return str; 
  	}
  
  //이메일 전송 
  public void mailSend(String email, String password){
      System.out.println("이메일 전송 완료!");
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(email); //받는 사람 주소
      message.setFrom(from_address); //보내는 사람 주소
      message.setSubject("PlaylistMate 임시 비밀번호 발급 안내 ");
      message.setText("임시 비밀번호는 " + password + " 입니다.");
      mailSender.send(message);
  }
  

  }