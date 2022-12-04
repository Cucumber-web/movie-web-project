package com.movie.back.entity;


import com.movie.back.dto.MemberRole;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


        @Builder.Default
        @ElementCollection(fetch = FetchType.LAZY)
        private Set<MemberRole> roleSet = new HashSet<>();


        @OneToMany(mappedBy = "member")
        @Builder.Default
        private Set<MemberMovie> movieSet= new HashSet<>();

}
