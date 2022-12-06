package com.movie.back.controller;


import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.service.MemberService;
import com.movie.back.service.MovieMemberService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

        @GetMapping("/exists")
        public ResponseEntity<Boolean> getExists(@RequestParam String title,HttpServletRequest request){
            String tokenStr = memberService.jwtExtract(request);
            Map<String,Object> values = jwtUtil.validateToken(tokenStr);
            return ResponseEntity.ok(myMovieService.exists(title,(String)values.get("email")));
        }

        @GetMapping("/list")
        public ResponseEntity<List<BoxOfficeDTO>> getList(HttpServletRequest request){
            String tokenStr = memberService.jwtExtract(request);
            Map<String,Object> values = jwtUtil.validateToken(tokenStr);

            return ResponseEntity.ok(myMovieService.getDtoList((String)values.get("email")));

        }

        @DeleteMapping("/remove")
        public ResponseEntity<String> remove(HttpServletRequest request,@RequestParam String title){
                String tokenStr = memberService.jwtExtract(request);
                Map<String,Object> values = jwtUtil.validateToken(tokenStr);

                myMovieService.deleteMyMovie((String)values.get("email"),title);
                return ResponseEntity.ok(null);

        }
}
