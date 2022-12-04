package com.movie.back.init;

import com.movie.back.service.BoxOfficeService;
import com.movie.back.service.KobisScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InitService implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private BoxOfficeService boxOfficeService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            System.out.println("DB 값 주입실행");
            boxOfficeService.saveBoxMovie();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (KobisScrapper.NotScrappedDateException e) {
            throw new RuntimeException(e);
        }
    }
}
