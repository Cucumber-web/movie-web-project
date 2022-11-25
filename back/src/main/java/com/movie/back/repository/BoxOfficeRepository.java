package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoxOfficeRepository extends JpaRepository<BoxOffice,String> {

    @Query("select distinct b from BoxOffice b left join fetch b.stillImage  " +
            " order by b.ranking asc  ")
    //fetch join 두개 존나위험해서 그냥 BatchSize사용함
    public List<BoxOffice> getBoxOfficeList();
    //left 없으면 null

    @Query("select distinct b from BoxOffice b left join fetch b.stillImage" +
            " where b.title = :title")
    public BoxOffice getMovieRead(String title);
}
