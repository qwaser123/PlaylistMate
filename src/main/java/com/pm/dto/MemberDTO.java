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
    
    //entity -> dto 변환
    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setPw(memberEntity.getPw());
        memberDTO.setEmail(memberEntity.getEmail());
        return memberDTO;
    }
    
}
