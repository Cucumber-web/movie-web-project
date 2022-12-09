package com.movie.back.service;

import com.movie.back.dto.CommentsData;
import com.movie.back.dto.MovieCommentsDTO;

import java.util.List;

public interface CommentsService {
    public CommentsData getDTOList(int page);

    public void saveComments(MovieCommentsDTO dto);

    public boolean modifyComment(MovieCommentsDTO dto);

    public boolean deleteCom(Long id,String email);
}
