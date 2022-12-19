package com.movie.back.dto.myData;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MyQuiz {

    private Long id;
    private String movieTitle;

    private String quizName;

}
