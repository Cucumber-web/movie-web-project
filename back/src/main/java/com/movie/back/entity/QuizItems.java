package com.movie.back.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class QuizItems {

        @Id
        private String itemTitle;

        private boolean correct;

        private String keyNumber;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "quiz_title")
        @ToString.Exclude
        private Quiz quiz;


}
