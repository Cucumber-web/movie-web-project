package com.movie.back.controller;


import com.movie.back.dto.QuizDTO;
import com.movie.back.service.MemberService;
import com.movie.back.service.QuizService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/save")   //
    public ResponseEntity<String> saveQuiz(HttpServletRequest request,
                                           @RequestBody QuizDTO quizDTO){
             String token = memberService.jwtExtract(request);
             Map<String,Object> map = jwtUtil.validateToken(token);
             quizService.saveQuiz(quizDTO.getMovieTitle(),(String) map.get("email"), quizDTO.getQuizTitle());
            //TODO 여기에 퀴즈 문항들도 추가해서 저장을 해야한다.
           return ResponseEntity.ok("저장완료");
    }//saveQuiz(String movieTitle,String email,String quizTitle)
}
