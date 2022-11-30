package com.movie.back.controller;


import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.service.BoxOfficeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchMovieController {

        private final BoxOfficeService boxOfficeService;


        @GetMapping (value="/detail")
        public ResponseEntity<BoxOfficeDTO> getSearchDetail(@RequestParam String title){

            return ResponseEntity.ok(boxOfficeService.getSerachMovie(title));
        }

        @GetMapping("/list")
        public ResponseEntity<List<BoxOfficeDTO>> getSearchMovie(@RequestParam(required = false) String title){
                return ResponseEntity.ok(boxOfficeService.getSearchMovieList(title));
        }

}
