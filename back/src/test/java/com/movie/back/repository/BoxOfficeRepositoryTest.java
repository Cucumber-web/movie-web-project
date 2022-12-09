package com.movie.back.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BoxOfficeRepositoryTest {


    @Autowired
    private BoxOfficeRepository boxOfficeRepository;

    @Test
    @Transactional
    void test(){
        boxOfficeRepository.getLikeList().subList(0,10).forEach(System.out::println);
    }
}