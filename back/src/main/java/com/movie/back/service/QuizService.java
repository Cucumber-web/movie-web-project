package com.movie.back.service;

import com.movie.back.dto.QuizDTO;
import com.movie.back.dto.QuizItems;

import java.util.List;

public interface QuizService {

    public List<QuizDTO> getQuiz(String title);

    public void saveQuiz(String movieTitle,String email,String quizTitle,List<QuizItems> quizItems);
}

