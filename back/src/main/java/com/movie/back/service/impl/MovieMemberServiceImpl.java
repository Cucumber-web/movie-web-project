package com.movie.back.service.impl;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.Member;
import com.movie.back.entity.MemberMovie;
import com.movie.back.repository.MemberMovieRepository;
import com.movie.back.service.MovieMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieMemberServiceImpl implements MovieMemberService {


    private final MemberMovieRepository memberMovieRepository;

    public void saveMovieMember(String email,String title){

            memberMovieRepository.save(MemberMovie.builder()
                            .member(Member.builder().email(email).build())
                            .boxOfficeId(BoxOffice.builder().title(title).build())
                    .build());
    }
}
