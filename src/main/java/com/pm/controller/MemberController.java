package com.pm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.pm.dto.MemberDTO;
import com.pm.service.MemberService;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }

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
        boolean result = memberService.emailCheck(email);
        if (result) {
            return "ok";
        } else {
            return "no";
        }
    }

	/* @GetMapping("/member/find") public String findForm() { return "find"; } */
    	
    @GetMapping("/pw-find")
    	public ModelAndView find() {
    		return new ModelAndView ("find");//member로 바꾸라
    	}
    
    @PostMapping("pw-find")
    public String findPw(@RequestBody MemberDTO login) throws Exception {
    	System.out.println("폼에서 받아온 email값 : " + login);
    	
    	return MemberService.findPw(login);  
    }
    
    @GetMapping("/member/find-id")
    public String findIdForm() {
        return "find-id";
    }

    
	/*
	 * @GetMapping("/member/email-check")
	 * 
	 * @ResponseBody public String emailCheck(@RequestParam("email") String email) {
	 * System.out.println("memberEmail = " + email); String checkResult =
	 * memberService.emailCheck(email); if (checkResult != null) { return "ok"; }
	 * else { return "no"; } }
	 */

}

/*@GetMapping("/check/findPw")
public @ResponseBody Map<String, Boolean> pw_find(String userEmail, String userName){
    Map<String,Boolean> json = new HashMap<>();
    boolean pwFindCheck = userService.userEmailCheck(userEmail,userName);

    System.out.println(pwFindCheck);
    json.put("check", pwFindCheck);
    return json;
}

//등록된 이메일로 임시비밀번호를 발송하고 발송된 임시비밀번호로 사용자의 pw를 변경하는 컨트롤러
@PostMapping("/check/findPw/sendEmail")
public @ResponseBody void sendEmail(String userEmail, String userName){
    MailDto dto = sendEmailService.createMailAndChangePassword(userEmail, userName);
    sendEmailService.mailSend(dto);

*/





