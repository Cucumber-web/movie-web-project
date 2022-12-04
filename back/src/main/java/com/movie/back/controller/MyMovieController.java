package com.movie.back.controller;


import com.movie.back.service.MemberService;
import com.movie.back.service.MovieMemberService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyMovieController {

        private final MemberService memberService;
        private final JWTUtil jwtUtil;

        private final MovieMemberService myMovieService;


        @PostMapping("/save/{title}")
        public ResponseEntity<String> getSave(@PathVariable String title,HttpServletRequest request){

            String tokenStr = memberService.jwtExtract(request);
            Map<String,Object> values = jwtUtil.validateToken(tokenStr);

            myMovieService.saveMovieMember((String)values.get("email"),title);

            return ResponseEntity.ok("저장완료");
        }
}
