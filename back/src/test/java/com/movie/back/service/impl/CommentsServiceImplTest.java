package com.movie.back.service.impl;

import com.movie.back.dto.MovieCommentsDTO;
import com.movie.back.entity.MovieComments;
import com.movie.back.service.CommentsService;
import com.movie.back.service.MovieMemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentsServiceImplTest {

    @Autowired
    CommentsService commentsService;

    @Test
    void test(){
        commentsService.saveComments(MovieCommentsDTO.builder()
                        .content("리뷰내용!~!!~!~!~!~!")
                        .email("user52")
                        .movieTitle("동감")
                        .spoiler(true)
                .build());
    }
}