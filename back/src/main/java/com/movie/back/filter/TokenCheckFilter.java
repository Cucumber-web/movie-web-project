package com.movie.back.filter;


import com.movie.back.security.exception.AccessTokenException;
import com.movie.back.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class TokenCheckFilter  extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

//        if(!path.startsWith("/api/")){  //요청경로가 api로 시작하지 않으면 그냥 지나가게 함
//            filterChain.doFilter(request,response); //필터를 따라 다음 필터로
//            return;
//        }
        if(path.startsWith("/register") || path.startsWith("/mvi/")){
            filterChain.doFilter(request,response);
            return;
        }
        log.info("Token Check Filter.....................");
        log.info("JWTUtil: "+jwtUtil);

        try{
            validateAccessToken(request);
            filterChain.doFilter(request,response);
        }catch (AccessTokenException accessTokenException){
            accessTokenException.sendResponseError(response);
        }
    }


    private Map<String,Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException{
        String headerStr = request.getHeader("Authorization");
        System.out.println(headerStr);
        if(headerStr == null || headerStr.length()<8){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        //Bearer 생략   Authorization: Bearer token 이런식으로 들어옴
        String tokenType = headerStr.substring(0,6);
        String tokenStr = headerStr.substring(7);

        if(tokenType.equalsIgnoreCase("Bearer") == false){
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try{
            Map<String,Object> values = jwtUtil.validateToken(tokenStr);

            return values;
        }catch (MalformedJwtException malformedJwtException){
            log.info("MalformedJwtException--------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        }catch (SignatureException signatureException){
            log.info("SignatureException---------------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        }catch (ExpiredJwtException e){
            //AccessToken 만료되면 여기로감
            log.error("ExpiredJwtExcpetion----------------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
        /*JWTUtil validateToken을 실행해서 문제가 생기면 발생하는 JwtException을 이용해서 예외 내용을 출력하고  예외를 던짐*/


    }
}
