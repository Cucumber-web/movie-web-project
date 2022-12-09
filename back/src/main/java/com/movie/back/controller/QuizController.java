package com.movie.back.controller;


import com.movie.back.dto.QuizDTO;
import com.movie.back.service.MemberService;
import com.movie.back.service.QuizService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/quiz/save")   //
    public ResponseEntity<Void> saveQuiz(HttpServletRequest request,
                                           @RequestBody QuizDTO quizDTO){
             String token = memberService.jwtExtract(request);
             Map<String,Object> map = jwtUtil.validateToken(token);
             quizService.saveQuiz(quizDTO.getMovieTitle(),(String) map.get("email"), quizDTO.getQuizTitle(),quizDTO.getQuizItems(),quizDTO.getCorrect());
            //TODO 여기에 퀴즈 문항들도 추가해서 저장을 해야한다.
           return  new ResponseEntity<Void>(HttpStatus.OK);
    }//saveQuiz(String movieTitle,String email,String quizTitle)

    @GetMapping("/mvi/problem")
    public ResponseEntity<List<QuizDTO>> getQuiz(@RequestParam String title){
            return ResponseEntity.ok(quizService.getQuiz(title));
    }
    @GetMapping("/solution")
    public ResponseEntity<Boolean> checkSolution(@RequestParam(required = true) String solution
                                                 ,@RequestParam(required = true) String quizTitle){
        //TODO: 고쳐야함
        return ResponseEntity.ok(true);
    }
}
