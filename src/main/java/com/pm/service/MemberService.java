package com.pm.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pm.dto.MailDTO;
import com.pm.dto.MailUtil;
import com.pm.dto.MemberDTO;
import com.pm.entity.MemberEntity;
import com.pm.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        // repository의 save메서드 호출 (조건. entity객체를 넘겨줘야 함)
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
            1. 회원이 입력한 이메일로 DB에서 조회를 함
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byEmail = memberRepository.findByEmail(memberDTO.getEmail());
        if (byEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byEmail.get();
            if (memberEntity.getPw().equals(memberDTO.getPw())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치(로그인실패)
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }

    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

	/*
	 * public String emailCheck(String email) { Optional<MemberEntity> byMeEmail =
	 * memberRepository.findByEmail(email); if (byMeEmail.isPresent()) { // 조회결과가 있다
	 * -> 사용할 수 없다. return null; } else { // 조회결과가 없다 -> 사용할 수 있다. return "ok"; } }
	 */

    public boolean emailCheck(String email) {
        Optional<MemberEntity> byMeEmail = memberRepository.findByEmail(email);
        return !byMeEmail.isPresent();
    }
    
	public static String findPw(MemberDTO login) throws Exception { //static- 수정!!
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = null;
		
		System.out.println("login 확인 : " + login);
		
		MemberDTO member = MemberRepository.login(login); //수정!
		
		if (member != null) {
			
			String tempPw = UUID.randomUUID().toString().replace("-", "");
			tempPw = tempPw.substring(0,10);
			
			System.out.println("임시 비밀번호 확인 : " + tempPw);
			
			member.setPw(tempPw);
			
			MailUtil mail  = new MailUtil();
			mail.sendmail(member); //수정!!
			
			String securepw = encoder.encode(member.getPw());
			member.setPw(securepw);
			
			MemberRepository.modifyPw(member); //수정!! -> 이거 말고 딴거로 바꿔야 되는데 이름이 머지
			
			result = "Success";
			
		}else {
			result = "Fail";
			
			
		}
		return result;
	}
    
    
    
    
    
    
    
    
    
    
	/*
	 * public MailDTO createMailAndChangePassword(String memberEmail) { String str =
	 * getTempPassword(); MailDTO dto = new MailDTO(); dto.setAddress(memberEmail);
	 * dto.setTitle("Cocolo 임시비밀번호 안내 이메일 입니다.");
	 * dto.setMessage("안녕하세요. Cocolo 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 " +
	 * str + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요"); updatePassword(str,memberEmail);
	 * return dto; }
	 * 
	 * //임시 비밀번호로 업데이트 public void updatePassword(String str, String userEmail){
	 * String memberPassword = str; Long memberId =
	 * mr.findByMemberEmail(userEmail).getId();
	 * mmr.updatePassword(memberId,memberPassword); }
	 * 
	 * //랜덤함수로 임시비밀번호 구문 만들기 public String getTempPassword(){ char[] charSet = new
	 * char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
	 * 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
	 * 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	 * 
	 * String str = "";
	 * 
	 * // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함 int idx = 0; for (int i = 0; i < 10;
	 * i++) { idx = (int) (charSet.length * Math.random()); str += charSet[idx]; }
	 * return str; } // 메일보내기 public void mailSend(MailDTO mailDTO) {
	 * System.out.println("전송 완료!"); SimpleMailMessage message = new
	 * SimpleMailMessage(); message.setTo(mailDTO.getAddress());
	 * message.setSubject(mailDTO.getTitle());
	 * message.setText(mailDTO.getMessage()); message.setFrom("보낸이@naver.com");
	 * message.setReplyTo("보낸이@naver.com"); System.out.println("message"+message);
	 * mailSender.send(message); }
	 * 
	 * //비밀번호 변경 public void updatePassWord(Long memberId, String memberPassword) {
	 * mmr.updatePassword(memberId,memberPassword); }
	 */
    
    
    
	/*
	 * public boolean userEmailCheck(String userEmail, String userName) {
	 * 
	 * User user = userRepository.findUserByUserId(userEmail); if(user!=null &&
	 * user.getName().equals(userName)) { return true; } else { return false; } }
	 */
    
}













