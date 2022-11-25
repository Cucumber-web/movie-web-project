package com.movie.back.entity;


import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class BoxOffice {

    @Id
    private String title;

    private Integer ranking;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    private String posterLink;

    private String date;
    @OneToMany(mappedBy = "boxOfficeId")
    private List<BoxStillImage> stillImage = new ArrayList<>();
    //단방향이어도 되지만 FK는 상대 테이블이 가지게 되어있기때문에 그냥 양방향함


    @BatchSize(size = 10)
    @OneToMany(mappedBy = "boxOfficeId")
    private List<ActorEntity> actorList = new ArrayList<>();


}
