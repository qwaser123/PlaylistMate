package com.pm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import com.pm.dto.MemberDTO;

@Entity
@Setter
@Getter
@Table(name = "member")
public class MemberEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;
    
    @Column
    private String name;
    
    @Column
    private String pw;
    
    @Column(unique = true) // unique 제약조건 추가
    private String email;

    

    

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setName(memberDTO.getName());
        memberEntity.setPw(memberDTO.getPw());
        memberEntity.setEmail(memberDTO.getEmail());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setName(memberDTO.getName());
        memberEntity.setPw(memberDTO.getPw());
        memberEntity.setEmail(memberDTO.getEmail());
        return memberEntity;
    }

}
