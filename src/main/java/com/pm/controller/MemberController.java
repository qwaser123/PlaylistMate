package com.pm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.pm.dto.MemberDTO;
import com.pm.service.MemberService;
import com.pm.service.SendEmailService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@EnableAutoConfiguration
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;
    
    @Autowired
    private SendEmailService sendemailService; 
    
    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
       
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO, Model model) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        if(memberService.emailCheck(memberDTO.getEmail()) == null) {
            model.addAttribute("errorMsg", "중복된 이메일"); 
            model.addAttribute("memberDTO", memberDTO);
            return "save";  
        }
        memberService.save(memberDTO);
        return "login"; //회원가입 성공
    } 
  //회원가입   	
    
    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getEmail());
            return "main";
        } else {
            // login 실패
            return "login";
        }
    }

    @GetMapping("/member/")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "main";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model) {
        String myEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("email") String email) {
        System.out.println("memberEmail = " + email);
        String checkResult = memberService.emailCheck(email);
        return checkResult; }
        
    //아이디 찾기
    @GetMapping("/member/find-id")
    public String findIdForm() {
        return "find-id";
    }

	 @GetMapping("/member/withdraw")
	    public String withdrawForm(HttpSession session, Model model) {
	        String loginEmail = (String) session.getAttribute("loginEmail");
	        if (loginEmail != null) {
	            MemberDTO memberDTO = memberService.withdrawForm(loginEmail);
	            model.addAttribute("member", memberDTO);
	            return "withdraw";
	        } else {
	            return "redirect:/member/login";
	        }
	    }

	    @PostMapping("/member/withdraw")
	    public String withdraw(HttpSession session, @RequestParam String pw) {
	    	String loginEmail = (String) session.getAttribute("loginEmail");
	    	if (loginEmail != null) {
	    		String pwCheckResult = memberService.pwWith(pw);
	    		if (pwCheckResult != null) {
	    			memberService.withdraw(loginEmail);
	    			session.invalidate();
	    			return "redirect:/member/login";
	    		} else {
	    			return "redirect:/member/withdraw?error=pw";
	    		}
	    	} else {
	    		return "redirect:/member/login";
	    	}
	    }

	    @PostMapping("/member/pw-withdraw")
	    public @ResponseBody String pwWith(@RequestParam("pw") String pw) {
	    	System.out.println("memberpw = " +pw);
	        String checkResult = memberService.pwWith(pw);
	        return checkResult;
	    }
	    
	    @PostMapping("/member/pw-check")
	    public @ResponseBody String pwCheck(@RequestParam("pw") String pw) {
	        System.out.println("memberpw = " +pw);
	        String checkResult = memberService.pwCheck(pw);
	        return checkResult;
	    } //비밀번호 변경 관련
	    
	    //get수정필요
	    @GetMapping("/pw-find") 
	    public String findForm() { 
	    	return "find"; 
	    	} 
	    
	    // 새로운 비밀번호 생성, 이메일 전송
	    @PostMapping("/send-password")
	    public String sendNewPassword(HttpServletRequest request) {
	        String email = request.getParameter("email"); 
	        String newPassword = sendemailService.createMailAndChangePassword(email);
	        sendemailService.mailSend(email, newPassword);
	        return "redirect:/member/login";
	    }
	    
}

    





