package com.movie.back.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member {

        @Id
        private String email;

        private String password;

        private String birth;

        private String gender;

        private String role;


        @OneToMany(mappedBy = "member")
        private List<MemberMovie> memberMovieList= new ArrayList<>();

}
