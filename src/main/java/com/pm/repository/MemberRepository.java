package com.pm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pm.dto.MemberDTO;
import com.pm.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 이메일로 회원 정보 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByEmail(String email);

	static MemberDTO login(MemberDTO login) {
		// TODO Auto-generated method stub
		return null;
	}

	static void modifyPw(MemberDTO member) {
		// TODO Auto-generated method stub
		
	}
}
