package com.pm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.pm.dto.MemberDTO;
import com.pm.entity.MemberEntity;
import com.pm.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	
	//이메일 중복체크 
    public String emailCheck(String email) {
    	 if (email == null || email.isEmpty()) {
    	        // 이메일 입력 안 된 경우 -> 사용할 수 없다.
    	        return "no";
    	    }
        Optional<MemberEntity> byMeEmail = memberRepository.findByEmail(email);
        if (byMeEmail.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }
    
    //암호화
	public interface PasswordEncoder {

		String encode(CharSequence rawPassword);
		boolean matches(CharSequence rawPassword, String encodedPassword);
		default boolean upgradeEncoding(String encodedPassword) {
			return false;
		}

	}
	
	 public MemberDTO withdrawForm(String email) {
	        Optional<MemberEntity> memberEntityWrapper = memberRepository.findByEmail(email);
	        if (memberEntityWrapper.isPresent()) {
	            MemberEntity memberEntity = memberEntityWrapper.get();
	            return MemberDTO.toMemberDTO(memberEntity);
	        } else {
	            return null;
	        }
	    }

	    public void withdraw(String email) {
	        Optional<MemberEntity> memberEntityWrapper = memberRepository.findByEmail(email);
	        if (memberEntityWrapper.isPresent()) {
	            MemberEntity memberEntity = memberEntityWrapper.get();
	            memberRepository.delete(memberEntity);
	        }
	    }

	    public String pwWith(String pw) {
	    	Optional<MemberEntity> byMePw = memberRepository.findByPw(pw);
	        if (byMePw.isPresent()) {
	            MemberEntity memberEntity = byMePw.get();
	            if (memberEntity.getPw().equals(pw)) {
	                return "ok";
	            } else {
	                return null;
	            }
	        } else {
	            return null;
	        }
	    }

	    public String pwCheck(String pw) {
	        Optional<MemberEntity> byMePw = memberRepository.findByPw(pw);
	      //비밀번호 조회 후 있는지 없는지 판단
	        if (byMePw.isPresent()) {
	            return null;
	        } else {
	            return "ok";
	        }
	    }

}
    
    
    












