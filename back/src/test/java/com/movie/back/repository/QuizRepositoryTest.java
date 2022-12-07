package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.Member;
import com.movie.back.entity.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuizRepositoryTest {


    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private BoxOfficeRepository boxOfficeRepository;


    @Test
    void 퀴즈생성하기(){
        BoxOffice boxOffice = boxOfficeRepository.findById("데시벨").get();
        Member member = Member.builder().email("user22").build();

        IntStream.rangeClosed(1,3).forEach(i -> {
                quizRepository.save(Quiz.builder()
                                .title("Quiz Title..."+i)
                                .member(member)
                                .boxOffice(boxOffice)
                        .build());
        });
    }

    @Test
    @Transactional
    void 킈즈꺼내오기(){
//
        boxOfficeRepository.getQuizeBoxOffice("데시벨").getQuizList().forEach(quiz -> {
                System.out.println("문항 -> "+quiz.getTitle());
                quiz.getQuizItems().forEach(System.out::println);
        });
    }
    @Test
    void 저장테스트(){
        Quiz quiz = Quiz.builder()
                .email("user22")
                .movieTitle("데시벨")
                .title("이건테스트용222")
                .build();

        quiz.addQuizItem("1번문항입니다");

        quiz.addQuizItem("2번문항입니다");

        quiz.addQuizItem("3번문항입니다");
        quizRepository.save(quiz);
    }
}