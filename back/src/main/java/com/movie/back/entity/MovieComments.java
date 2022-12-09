package com.movie.back.entity;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MovieComments {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        public MovieComments(String content,boolean spoiler,String movieTitle,String email){
                this.content = content;
                this.spoiler = spoiler;
                this.movieTitle = movieTitle;
                this.email = email;
        }
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "boxoffice_id",insertable = false,updatable = false)
        @ToString.Exclude
        private BoxOffice boxOffice;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="email",insertable = false,updatable = false)
        @ToString.Exclude
        private Member member;

        @Column(name = "boxoffice_id")
        private String movieTitle;

        @Column(name = "email")
        private String email;


        @Column(columnDefinition = "TEXT")
        private String content;



        private boolean spoiler;

        @CreatedDate
        private LocalDateTime createdAt;

        @LastModifiedDate
        private LocalDateTime modifiedAt;


        public void changeComments(String content,boolean spoiler){
                this.content = content;
                this.spoiler = spoiler;
        }
}
