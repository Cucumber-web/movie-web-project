package com.movie.back.service.impl;


import com.movie.back.dto.QuizDTO;
import com.movie.back.dto.QuizItems;
import com.movie.back.entity.Quiz;
import com.movie.back.repository.BoxOfficeRepository;
import com.movie.back.repository.QuizRepository;
import com.movie.back.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final BoxOfficeRepository boxOfficeRepository;
    private final QuizRepository quizRepository;

    public List<QuizDTO> getQuiz(String title){
        List<QuizDTO> quizDTOList = new ArrayList<>();
        boxOfficeRepository.getQuizeBoxOffice(title)
                .getQuizList().forEach(quiz -> {

                quizDTOList.add(QuizDTO.builder()
                                .id(quiz.getId())
                                .quizTitle(quiz.getTitle())
                                .movieTitle(quiz.getMovieTitle())
                        .build());
        });
        return quizDTOList;
    }

    public List<String> getItems(String quizTitle){
     //       var list =quizRepository.getQuizByQuizTitle(quizTitle).get().getQuizItems().stream().collect(Collectors.toList());
       //     Collections.sort(list);
       // return list;
        return null;
    }

    public void saveQuiz(String movieTitle,String email,String quizTitle ,List<QuizItems> quizItems,String correct){
            Quiz quiz =Quiz.builder()
                    .movieTitle(movieTitle)
                    .email(email)
                    .title(quizTitle)
                    .build();

        quizItems.forEach(quizItem -> {
             if(quizItem.getItem().equals(correct)){
                 quiz.addQuizItem(quizItem.getItem(),quizItem.getKey(),true);
             }else{
                 quiz.addQuizItem(quizItem.getItem(),quizItem.getKey(),false);
             }
        });
        quizRepository.save(quiz);
    }


}
