package com.movie.back.service;

import com.movie.back.repository.QuizItemsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuizServiceTest {

    @Autowired
    QuizService quizService;

    @Autowired
    QuizItemsRepository quizItemsRepository;


    @Test
    void 서비스퀴즈테스트(){

            quizService.getQuiz("데시벨").forEach(System.out::println);

            //TODO: 영화제목을 넣으면 퀴즈가 나오는 것까지 완료함 이제 퀴즈 생성, 퀴즈 권한체크(이거고민중)
    }

    @Test
    @Transactional
    void 퀴즈문항테스트(){
        quizService.getItems("이것이 데시벨에 퀴즈입니다").forEach(s -> {
            if(s.startsWith("1")){
                System.out.println("1번 문항"+s);
            }else if(s.startsWith("2")){
                System.out.println("2번 문항"+s);
            }else if(s.startsWith("3")){
                System.out.println("3번 문항"+s);
            }
        });
    }

    @Test
    void 지우기(){
        quizItemsRepository.deleteAll();
    }
}