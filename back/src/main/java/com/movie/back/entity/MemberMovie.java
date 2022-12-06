package com.movie.back.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MemberMovie { //찜한영화

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    @ToString.Exclude
    private Member member;

    @ManyToOne
    @JoinColumn(name="boxOffice_id")
    private BoxOffice boxOfficeId;  //이건 단방향 영화정보가 이 정보를 알 필요가 없음


    public void changeMember(Member member){
        this.member = member;
    }

}
