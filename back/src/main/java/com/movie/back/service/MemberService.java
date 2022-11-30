package com.movie.back.service;


import com.movie.back.dto.MemberDTO;
import com.movie.back.entity.Member;
import com.movie.back.repository.MemberRepository;
import com.movie.back.security.exception.AccessTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberDTO memberRegister(MemberDTO memberDTO){
            memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
            return MemberDTO.toDTO(memberRepository.save(MemberDTO.toEntity(memberDTO)));
    }

    public String jwtExtract(HttpServletRequest request){
        String headerStr = request.getHeader("Authorization");

        String tokenType = headerStr.substring(0,6);
        String tokenStr = headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        return tokenStr;
    }
}
