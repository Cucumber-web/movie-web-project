package com.movie.back.repository;

import com.movie.back.dto.MemberDTO;
import com.movie.back.dto.MemberRole;
import com.movie.back.dto.RegisterBody;
import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.Member;
import com.movie.back.entity.MemberMovie;
import com.movie.back.service.MovieMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoxOfficeRepository boxOfficeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberMovieRepository memberMovieRepository;

    @Autowired
    MovieMemberService myMovieService;

    @Test
    void 아이디등록하기(){
        memberRepository.save(Member.builder().email("user22")
                .password(passwordEncoder.encode("1111"))
                .roleSet(Set.of(MemberRole.ADMIN))
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
                .password(passwordEncoder.encode("1111")).roleSet(Set.of(MemberRole.ADMIN)).build()).getAuthorities().forEach(System.out::println);
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

    @Test
    @Transactional      //role 접근할떄 꼭 필요
    void memberInfo(){
        System.out.println(memberRepository.getMemberInfo("user").get());
        //getMemberInfo를 통해 같이 불러오도록 쿼리 만듬
    }
    @Test
    void movieMember(){
       // BoxOffice boxOffice = boxOfficeRepository.getMovieRead("동감");
      //  Member member = memberRepository.getMemberInfo("user").get();
        //찜하기
        MemberMovie movie = MemberMovie.builder()
                .member(Member.builder().email("user").build())
                .boxOfficeId(BoxOffice.builder().title("데시벨").build())
                .build();
        memberMovieRepository.save(movie);


    }

    @Test
    @Transactional
    void movieAll(){
//        memberRepository.getMyMovie("user")
//                .get()
//                .getMovieSet().forEach(memberMovie -> {
//                    System.out.println(memberMovie.getBoxOfficeId());
//        });
        //memberMovieRepository.memberMyMovie("user").forEach(boxOffice -> System.out.println(boxOffice.getTitle()));
            //이메일을 넣으면 inner join으로 BoxOffice 엔티티 객체를 불러옴
        // inner join 으로 조인키와 pk가 같은 on 을 걸어서 가져옴 - 중복제거 완료

    }
    @Test
    @Transactional
    void 찜한영화테스트(){
      //  myMovieService.getDtoList("user").forEach(System.out::println);
    }

    @Test
    void tst(){

        MemberDTO dto = MemberDTO.toDTO(Member.builder().email("user")
                .password(passwordEncoder.encode("1111"))
                .roleSet(Set.of(MemberRole.ADMIN))
                .gender("남")
                .birth(LocalDateTime.now().minusYears(25).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build());
        dto.setAgeGroup(dto.getBirth());

        System.out.println(dto);
        memberRepository.save(MemberDTO.toEntity(dto));
    }

    @Test
    @Rollback(value = false)
    @Transactional
    void tt(){
//        RegisterBody registerBody = RegisterBody.builder()
//                .email("test11")
//                .password(passwordEncoder.encode("1111"))
//                .role("ROLE_ADMIN")
//                .birth("1999-11-11")
//                .gender("남")
//                .build();
//        MemberDTO dto = registerBody.toMemberDTO(registerBody);
//        dto.setAgeGroup(dto.getBirth());
//
//        System.out.println(dto); //DTO  생서앚쪽에 ADMIN 넣엇음
//        Member member = MemberDTO.toEntity(dto);
//        System.out.println(" 엔티티화 ==="+member);

      //  memberRepository.save(member);

        Member member =memberRepository.getMemberInfo("test11").get();
         member.addRole(MemberRole.ADMIN);
        memberRepository.save(member);

     //   member.getRoleSet().forEach(System.out::println);
    }
}
