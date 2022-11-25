package com.movie.back.service;


import com.movie.back.dto.MemberDTO;
import com.movie.back.entity.Member;
import com.movie.back.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO memberRegister(MemberDTO memberDTO){
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
            return MemberDTO.toDTO(memberRepository.save(MemberDTO.toEntity(memberDTO)));
    }
}
