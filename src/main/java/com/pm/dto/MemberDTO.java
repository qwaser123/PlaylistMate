package com.pm.dto;

import com.pm.entity.MemberEntity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String name;
    private String pw;
    private String email;
    
	/*
	 * private String address; private String title; private String message;
	 */
    

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setPw(memberEntity.getPw());
        memberDTO.setEmail(memberEntity.getEmail());
        return memberDTO;
    }
}
