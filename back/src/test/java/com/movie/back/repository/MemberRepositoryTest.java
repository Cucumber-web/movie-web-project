package com.movie.back.repository;

import com.movie.back.dto.MemberDTO;
import com.movie.back.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void 아이디등록하기(){
        memberRepository.save(Member.builder().email("user")
                .password(passwordEncoder.encode("1111"))
                .role("ROLE_ADMIN")
                .gender("남")
                .birth(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build());

    }

    @Test
    void test2() {
        String str = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Njg2ODgyNTcsImlhdCI6MTY2ODY4ODE5NywiZW1haWwiOiJ1c2VyIn0.GMQ4FPxVcTwW-kQyzSZgtGaXxCjA2bLhx_GdObvTN20";
        String str2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Njg2ODgzOTYsImlhdCI6MTY2ODY4ODMzNiwiZW1haWwiOiJ1c2VyIn0.bSWYz33lmJMidy3PRCMw8i4c7sETOrQssHwzfaQZf7w";
    }

    @Test
    void entityToDTO(){
       MemberDTO.toDTO(Member.builder().email("ta33@naver.com").gender("남").birth(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .password(passwordEncoder.encode("1111")).role("ROLE_ADMIN").build()).getAuthorities().forEach(System.out::println);
    }

    @Test
    void registerUser(){
                Member member = memberRepository.findById("user").get();
                System.out.println(MemberDTO.toDTO(member));
                 //toString으로는 권한이 뽑히지 않지만 권한을 가지고 있음을 알 수 있음 상속받은 놈이 가지고 있음 그걸
                // toDTO를 통해 만들 수 있게함
               MemberDTO.toDTO(member).getAuthorities().forEach(System.out::println);
    }
    @Test
    void userOut(){
            memberRepository.findAll().forEach(System.out::println);
    }
}
