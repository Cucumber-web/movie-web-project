package com.movie.back.service;

import com.movie.back.dto.BoxOfficeDTO;

import java.util.List;

public interface MovieMemberService {

    public void saveMovieMember(String email,String title);
    public List<BoxOfficeDTO> getDtoList(String email);
    public void deleteMyMovie(String email,String title);
    public boolean exists(String title,String email);
}
