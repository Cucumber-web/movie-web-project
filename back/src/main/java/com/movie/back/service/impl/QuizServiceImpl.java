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
import java.util.List;

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

    public void saveQuiz(String movieTitle,String email,String quizTitle,List<QuizItems> quizItems){
        Quiz quiz =Quiz.builder()
                .movieTitle(movieTitle)
                .email(email)
                .title(quizTitle)
                .build();

        quizItems.forEach(quizItem -> {
             quiz.addQuizItem(quizItem.getItem());
        });
        quizRepository.save(quiz);
    }
}
