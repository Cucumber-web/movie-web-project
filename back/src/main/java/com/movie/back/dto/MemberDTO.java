package com.movie.back.dto;

import com.movie.back.entity.Member;
import com.movie.back.entity.MemberMovie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private String email;

    private String password;

    private String birth;

    private String gender;

    private Set<MemberRole> role;

    private List<MemberMovieDTO> memberMovieList;
    public MemberDTO(String username, String password,Collection<GrantedAuthority> authorities,String birth,String gender){
        super(username,password,authorities);
        this.email = username;
        this.password = password;
        this.birth = birth;
        this.gender = gender;
    }


    public static Member toEntity(MemberDTO memberDTO){
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .gender(memberDTO.getGender())
                .roleSet(memberDTO.getRole())
                .birth(memberDTO.getBirth())
                .build();

        return member ;
    }

    //toDTO를 통해 바꾼 값만 MemberDTO가 그 값을 가지고 있음 !!
    public static MemberDTO toDTO(Member member){   //member 엔티티는 권한 빼고 모든 값이 채워지면 됨 권한은 알아서 넘김
            //    Set<String> roleTypes = Set.of(member.getRole());   //dto는 User를 상속하기에 authorities 변수가 위에 있음

                return new MemberDTO(member.getEmail(), member.getPassword(),
                        member.getRoleSet().stream().map(memberRole ->
                                new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toUnmodifiableSet()),
                        member.getBirth(), member.getGender());
    }
}
