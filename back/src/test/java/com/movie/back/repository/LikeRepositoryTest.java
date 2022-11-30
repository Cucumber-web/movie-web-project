package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.LikeGood;
import com.movie.back.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class LikeRepositoryTest {

    @Autowired
    LikeRepository likeRepository;


    @Test
    void saveTest(){
            likeRepository.save(LikeGood.builder()
                            .member(Member.builder().email("user").build())
                            .boxOffice(BoxOffice.builder().title("헤어질 결심").build())
                    .build());
    }

    @Test
    void exitTest(){//성공
        System.out.println(likeRepository.exists(BoxOffice.builder().title("헤어질 결심").build()
                ,Member.builder().email("user").build()));

    }
    @Test
    void exitTest2(){
        System.out.println(likeRepository.likeElement(
                BoxOffice.builder().title("헤어질 결심").build(), Member.builder().email("user").build())
        );

    }
}