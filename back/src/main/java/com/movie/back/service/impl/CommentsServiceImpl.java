package com.movie.back.service.impl;

import com.movie.back.dto.CommentsData;
import com.movie.back.dto.MovieCommentsDTO;
import com.movie.back.entity.MovieComments;
import com.movie.back.repository.MovieCommentRepository;
import com.movie.back.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
        private final MovieCommentRepository movieCommentRepository;

        public CommentsData getDTOList(int page){
            List<MovieCommentsDTO> dtoList = new ArrayList<>();
            Page<MovieComments> pageList =movieCommentRepository
                    .findAll(PageRequest.of(page,10));
            pageList.forEach(
                    movieComments -> {
                        dtoList.add(MovieCommentsDTO.builder()
                                        .id(movieComments.getId())
                                        .email(movieComments.getEmail())
                                        .movieTitle(movieComments.getMovieTitle())
                                        .content(movieComments.getContent())
                                        .spoiler(movieComments.isSpoiler())
                                        .createdAt(movieComments.getCreatedAt())
                                .build());
                    }
            );

            return CommentsData.builder().dtoList(dtoList).totalPage(pageList.getTotalPages()).build();
        }

        @Override
        public void saveComments(MovieCommentsDTO dto) {
                movieCommentRepository.save(
                        new MovieComments(dto.getContent()
                                ,dto.isSpoiler()
                                ,dto.getMovieTitle()
                                ,dto.getEmail())
                );
        }


        public boolean modifyComment(MovieCommentsDTO dto){

            MovieComments comments = movieCommentRepository.findById(dto.getId()).get();
            System.out.println(dto.getEmail());
            System.out.println(comments.getEmail());
            if(dto.getEmail().equals(comments.getEmail())){
                comments.changeComments(dto.getContent(),dto.isSpoiler());
                movieCommentRepository.save(comments);
                return  true;
            }else{
                return false;
            }
        }

    @Override
    public boolean deleteCom(Long id,String email) {
        MovieComments movieComments = movieCommentRepository.findById(id).orElseThrow(RuntimeException::new);
        if(movieComments.getEmail().equals(email)){
            movieCommentRepository.delete(movieComments);
            return true;
        }else{
            return false;
        }
    }


}
