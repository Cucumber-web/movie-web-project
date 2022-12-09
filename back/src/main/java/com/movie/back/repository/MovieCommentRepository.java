package com.movie.back.repository;

import com.movie.back.entity.MovieComments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCommentRepository extends JpaRepository<MovieComments,Long> {

    public Page<MovieComments> findAll(Pageable pageable);
}
