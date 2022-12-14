package com.movie.back.config;


import com.movie.back.filter.APILoginFilter;
import com.movie.back.filter.RefreshTokenFilter;
import com.movie.back.filter.TokenCheckFilter;
import com.movie.back.handler.APILoginFailureHandler;
import com.movie.back.handler.APILoginSuccessHandler;
import com.movie.back.security.CustomUserDetailService;
import com.movie.back.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class CustomSercurityConfig {



    private final CustomUserDetailService customUserDetailService;

    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

        //Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authenticationManager(authenticationManager);

        //APILoginFilter ??????
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        //APILoginSuccessHandler    ?????? ???????????? ????????? ?????????????????? jwtUtil??? ????????? ???????????????
        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
        //Handler ??????
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler); //?????? ???????????? redirect ??? ?????? ??? ???????????? ?????????
        //????????? ?????? ????????????
        apiLoginFilter.setAuthenticationFailureHandler(new APILoginFailureHandler());
        //apiLoginFilter.setAuthenticationFailureHandler();
        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);   //????????????

        http.addFilterBefore(tokenCheckFilter(jwtUtil,customUserDetailService),UsernamePasswordAuthenticationFilter.class); //?????? ?????? ??????
        //UsernamePassword??? ???????????? ????????? UsernamePasswrodAuthenticationFIlter ?????? ?????????????????? ??????
        //refreshToken ??????
        http.addFilterBefore(new RefreshTokenFilter("/refreshToken",jwtUtil),TokenCheckFilter.class);

       // log.info("-------------------Web Configure-------------------");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //????????? ???????????? ???????????? ???

        return http.build();
    }

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil,CustomUserDetailService customUserDetailService){
        return new TokenCheckFilter(customUserDetailService,jwtUtil);
    }
}
