package com.movie.back.repository;

import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.LikeGood;
import com.movie.back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeGood,Long> {


    @Query("select count(l.id) > 0 from LikeGood l " +
            "where l.boxOffice = :title and  l.member = :email")
    boolean exists(@Param("title") BoxOffice title, @Param("email") Member email);

    @Query("select distinct l  from LikeGood l " +
            "left join fetch l.boxOffice join fetch l.member where l.boxOffice = :title and l.member = :email")
    Optional<LikeGood> likeElement(@Param("title") BoxOffice title, @Param("email") Member email);
    //이거 한 개만 가져오기에 패치 두번 문제없음


    @Query("delete from LikeGood l where l.boxOffice = :boxOffice and l.member = :member")
    void deleteLike(@Param("boxOffice") BoxOffice boxOffice,@Param("member") Member member);
}
