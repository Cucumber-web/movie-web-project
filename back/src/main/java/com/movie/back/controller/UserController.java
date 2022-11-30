package com.movie.back.controller;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.back.data.MemberRequest;
import com.movie.back.dto.MemberDTO;
import com.movie.back.dto.RegisterBody;
import com.movie.back.security.exception.AccessTokenException;
import com.movie.back.service.LikeService;
import com.movie.back.service.MemberService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

        private final MemberService memberService;

        private final LikeService likeService;

        private final JWTUtil jwtUtil;

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody  RegisterBody registerBody)
    {
        System.out.println(registerBody);
            MemberDTO dto = registerBody.toMemberDTO(registerBody);
            if(memberService.memberRegister(dto) != null){
                return ResponseEntity.ok("회원가입 성공");
            }else{
                return ResponseEntity.ok("실패");
            }
    }

    @GetMapping("/like")    //토큰 넘겨줘야 실행가능함 토큰과 영화제목을 넘겨주어야 좋아요가 저장됨
    public ResponseEntity<String> like(@RequestParam String title, HttpServletRequest request){

        String tokenStr = memberService.jwtExtract(request);
        Map<String,Object> values = jwtUtil.validateToken(tokenStr);

        likeService.saveLike((String)values.get("email"),title);

        return ResponseEntity.ok("좋아요");
    }

    @GetMapping("/like/read")       //좋아요를 눌렀으면 트루 아니면 풜스 이상한 값 넘어오면 풜스
    public ResponseEntity<Boolean> existLike(@RequestParam String title,HttpServletRequest request){
            String tokenStr = memberService.jwtExtract(request);
            Map<String,Object> values = jwtUtil.validateToken(tokenStr);

            Boolean exist = likeService.existByLike((String)values.get("email"),title);

           return ResponseEntity.status(200).body(exist);
    }

    @DeleteMapping("/like/delete")
    public ResponseEntity<String> deleteLike(@RequestParam String title,HttpServletRequest request){
            String tokenStr = memberService.jwtExtract(request);
            Map<String,Object> values = jwtUtil.validateToken(tokenStr);

            likeService.deleteLike((String)values.get("email"),title);

        return ResponseEntity.status(200).body("삭제완료");
    }
}
