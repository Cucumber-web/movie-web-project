package com.movie.back.controller;


import com.movie.back.data.MemberRequest;
import com.movie.back.dto.MemberDTO;
import com.movie.back.dto.RegisterBody;
import com.movie.back.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

        private final MemberService memberService;

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody  RegisterBody registerBody)
    {
        System.out.println(registerBody);
            MemberDTO dto = registerBody.toMemberDTO(registerBody);
            if(memberService.memberRegister(dto) != null){
                return ResponseEntity.ok("회원가입 성공");
            }else{
                return ResponseEntity.ok("실패");
            }
    }
}
