package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.MemberMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberMovieRepository extends JpaRepository<MemberMovie,Long> {

        @Query("select distinct m.boxOfficeId from MemberMovie m where m.member.email = :email")
        public List<BoxOffice> memberMyMovie(@Param("email") String email);
}
