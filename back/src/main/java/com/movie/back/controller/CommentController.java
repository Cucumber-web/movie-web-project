package com.movie.back.controller;


import com.movie.back.dto.CommentsData;
import com.movie.back.dto.MovieCommentsDTO;
import com.movie.back.entity.MovieComments;
import com.movie.back.service.CommentsService;
import com.movie.back.service.MemberService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {

        private final MemberService memberService;
        private final JWTUtil jwtUtil;
        private final CommentsService commentsService;



        @GetMapping("/comments/list")
        public ResponseEntity<CommentsData> getList(@RequestParam(defaultValue = "0") int page){
            return ResponseEntity.ok(commentsService.getDTOList(page));
        }

        @PostMapping("/comments/save")
        public ResponseEntity<Boolean> saveComments(@RequestBody MovieCommentsDTO dto, HttpServletRequest httpServletRequest) {
                String token = memberService.jwtExtract(httpServletRequest);
                Map<String,Object> result = jwtUtil.validateToken(token);
                dto.setEmail((String)result.get("email"));
                commentsService.saveComments(dto);

                return ResponseEntity.ok(true);
        }

        @PutMapping("/comments/modify")
        public ResponseEntity<Boolean> modifyComments(@RequestBody MovieCommentsDTO dto,HttpServletRequest request){
                String token = memberService.jwtExtract(request);
                Map<String,Object> result = jwtUtil.validateToken(token);
                dto.setEmail((String)result.get("email"));

                if(commentsService.modifyComment(dto)){
                        return ResponseEntity.ok(true);
                }else{
                        return ResponseEntity.ok(false); //사용자가 다른 사용자일떄 바뀌지 않음
                }
        }


        @DeleteMapping("/comments/delete")
        public ResponseEntity<Boolean> deleteComments(@RequestParam(required = true) Long id,HttpServletRequest request){
                String token = memberService.jwtExtract(request);
                Map<String,Object> result = jwtUtil.validateToken(token);
                if(commentsService.deleteCom(id,(String)result.get("email"))){
                        return ResponseEntity.ok(true);
                }else{
                        return ResponseEntity.ok(false); //사용자가 다른 사용자일떄 바뀌지 않음
                }
        }
}
