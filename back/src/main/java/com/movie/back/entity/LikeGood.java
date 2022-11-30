package com.movie.back.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class LikeGood {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_id")
        @ToString.Exclude
        private BoxOffice boxOffice;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "email")
        @ToString.Exclude
        private Member member;
}
