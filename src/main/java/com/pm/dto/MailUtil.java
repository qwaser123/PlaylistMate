/*
 * package com.pm.dto;
 * 
 * import org.apache.commons.mail.HtmlEmail;
 * 
 * import com.pm.entity.MemberEntity;
 * 
 * public class MailUtil {
 * 
 * public void sendmail(MemberDTO member) throws Exception { //수정!!
 * 
 * String charSet ="utf-8"; String hostSMTP = "stmp.naver.com"; String
 * hostSMTPid = "1120ksh"; String hostSMTPpw = "wndnjs123!";
 * 
 * 
 * String fromEmail = "1120ksh@naver.com"; String fromName = "PlaylistMate";
 * 
 * String subject = ""; String msg = "";
 * 
 * subject = "[PlaylistMate] 임시 비밀번호 발급 안내"; msg += "<div align='left'>"; msg +=
 * "<h3'>"; msg += member.getEmail() +
 * "님의 임시 비밀번호 입니다. <br> 비밀번호를 변경하여 사용하세요.</h3>";
 * 
 * msg += "<p>임시 비밀번호 : "; msg += member.getPw() + "</p></div>";
 * 
 * //email 전송 String mailRecipient = member.getEmail(); try { HtmlEmail mail =
 * new HtmlEmail(); mail.setDebug(true); mail.setCharset(charSet);
 * mail.setSSLOnConnect(true); mail.setHostName(hostSMTP);
 * mail.setSmtpPort(587); mail.setAuthentication(hostSMTPid, hostSMTPpw);
 * mail.setStartTLSEnabled(true); mail.addTo(mailRecipient, charSet);
 * mail.setFrom(fromEmail, fromName, charSet); mail.setSubject(subject);
 * mail.setHtmlMsg(msg); mail.send();
 * 
 * } catch (Exception e) { e.printStackTrace(); } } }
 */