package com.movie.back.repository;

import com.movie.back.entity.MemberMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberMovieRepository extends JpaRepository<MemberMovie,Long> {
}
