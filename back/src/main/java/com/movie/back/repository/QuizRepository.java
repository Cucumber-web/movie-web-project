package com.movie.back.repository;

import com.movie.back.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

        @Query("select q from Quiz q where q.boxOffice.title = :title")
        public List<Quiz> getQuiz(@Param("title") String title);
}
