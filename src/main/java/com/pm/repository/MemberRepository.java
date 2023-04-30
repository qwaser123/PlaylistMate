package com.pm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pm.dto.MemberDTO;
import com.pm.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 이메일로 회원 정보 조회 (select * from member_table where member_email=?)
 

	static MemberDTO login(MemberDTO login) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 이메일로 아이디 조회
    @Query("SELECT id FROM MemberEntity WHERE email = :email")
    Integer findIdByEmail(@Param("email") String email);

    //  비밀번호 업데이트
    @Modifying
    @Query("UPDATE MemberEntity SET password = :password WHERE id = :id")
    void updateUserPassword(@Param("id") Long id, @Param("password") String password);
    
    Optional<MemberEntity> findByEmail(String email);
    Optional<MemberEntity> findByPw(String pw);
    Optional<MemberEntity> findById(String id);
    
}
