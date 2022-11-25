package com.movie.back.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.service.BoxOfficeService;
import com.movie.back.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mvi")
@RequiredArgsConstructor
public class MainController {

    private final BoxOfficeService boxOfficeService;

    @GetMapping(value = "/box")    //전체 데이터 조회
    public ResponseEntity<List<BoxOfficeDTO>> readAll(){
            return ResponseEntity.ok(boxOfficeService.getBoxList());
    }

    @GetMapping(value = "/read")
    public ResponseEntity<BoxOfficeDTO> read(@RequestParam(required = true) String title){

        return ResponseEntity.ok(boxOfficeService.getReadMovie(title));
    }


    @PostMapping(value = "",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(){
            return ResponseEntity.ok("post");
    }
    @PutMapping(value = "/{movieId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> modify(
            @PathVariable("movieId") Long movieId){
        return ResponseEntity.ok("put");
    }
    @DeleteMapping(value = "/{movieId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> remove(@PathVariable("movieId") Long movieId){
            return ResponseEntity.ok(1L);   //todo: 삭제한 PK가 날아가게
    }



}
