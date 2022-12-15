package com.movie.back.controller.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdviceProcess {


        @ExceptionHandler(Exception.class)
        public String errorHandler(Exception e){
            return "잘못된 값이 들어옴";
        }

       // @ResponseStatus(HttpStatus.NOT_FOUND)
        @ResponseStatus(HttpStatus.OK)
        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<String> handle404(NoHandlerFoundException exception) {
            return ResponseEntity.ok("<h3>404</h3>");    //404에러시 404 값이 넘어가게 바꿈
        }

}
