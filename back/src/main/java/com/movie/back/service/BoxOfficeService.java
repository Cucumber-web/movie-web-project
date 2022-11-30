package com.movie.back.service;


import com.movie.back.data.cdata.Actor;
import com.movie.back.data.cdata.MovieCode;
import com.movie.back.dto.ActorDTO;
import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.entity.ActorEntity;
import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.BoxStillImage;
import com.movie.back.repository.ActorRepository;
import com.movie.back.repository.BoxOfficeRepository;
import com.movie.back.repository.BoxStillImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoxOfficeService {

    private final BoxOfficeRepository boxOfficeRepository;
    private final BoxStillImageRepository boxStillImageRepository;
    private final ActorRepository actorRepository;

    private final ScrapperService scrapperService;

    public void saveBoxMovie() throws IOException, KobisScrapper.NotScrappedDateException {
        for (BoxOfficeDTO boxOfficeDTO : scrapperService.latestBoxOffice()) {
            BoxOffice boxOffice = BoxOffice.builder()
                    .title(boxOfficeDTO.getTitle())
                    .ranking(boxOfficeDTO.getRank())
                    .synopsis(boxOfficeDTO.getSynopsis())
                    .date(boxOfficeDTO.getDate())
                    .posterLink(boxOfficeDTO.getPostLink())
                    .build();
            boxOfficeRepository.save(boxOffice);    //연관관계 없는 것들 먼저 저장한다.

            boxOfficeDTO.getStillImage().forEach(image ->{  //불러온 이미지들을 위에 저장한 정보에 연관관계로 저장
                boxStillImageRepository.save(BoxStillImage.builder()
                        .boxOfficeId(boxOffice)
                        .imageLink(image)
                        .build());
            });
            boxOfficeDTO.getActorList().forEach(actor -> {
                actorRepository.save(ActorEntity.builder()
                                .actorName(actor.getActorName())
                                .actorRole(actor.getActorRole())
                                .boxOfficeId(boxOffice)
                        .build());
            });
        }
    }

    @Transactional(readOnly = true)
    public List<BoxOfficeDTO> getBoxList(){
            List<BoxOfficeDTO> dtoList = new ArrayList<>();


            boxOfficeRepository.getBoxOfficeList().forEach(boxOffice -> {


                dtoList.add(BoxOfficeDTO.builder()
                                .title(boxOffice.getTitle())
                                .rank(boxOffice.getRanking())
                                .postLink(boxOffice.getPosterLink())
                                .date(boxOffice.getDate())
                                .synopsis(boxOffice.getSynopsis())
                        .build());

            });
            return dtoList;

    }

    public BoxOfficeDTO getReadMovie(String title){
            BoxOffice boxOffice = boxOfficeRepository.getMovieRead(title);
        List<String> resultStill = null;
            List<String> still = boxOffice
                    .getStillImage().stream().map(boxStillImage -> boxStillImage.getImageLink())
                    .collect(Collectors.toList());
            if(!still.isEmpty()){
                resultStill = still.subList(0,10);
            }
        return BoxOfficeDTO.builder()
                .title(boxOffice.getTitle())
                .date(boxOffice.getDate())
                .synopsis(boxOffice.getSynopsis())
                .stillImage(resultStill)
                .postLink(boxOffice.getPosterLink())
                .rank(boxOffice.getRanking())
                .actorList(boxOffice.getActorList().stream().map(actorEntity -> ActorDTO.builder()
                        .actorName(actorEntity.getActorName())
                        .actorRole(actorEntity.getActorRole())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Transactional
    public void saveSearchBoxOffice(int startYear,int endYear){ //크롤링한 검색을 위한 데이터들을 저장하는 곳이다.
        IntStream.rangeClosed(1,34).forEach(page -> {
            MovieCode[] movieCodes = new MovieCode[0];
            String synopsis = "";
            String posterLink = "";
           try{
               movieCodes =KobisScrapper.searchUserMovCdList(startYear,endYear,page);
           }catch (NullPointerException nullPointerException){
               log.info("페이지를 불러오는데 실패함");
           }

            for(MovieCode movie : movieCodes){
                try {
                    synopsis =  Optional.of(KobisScrapper.getSynopsisByCode(movie.getCode())).orElse("없음");
                    posterLink = KobisScrapper.getMainPosterByCode(movie.getCode());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                BoxOffice boxOffice = BoxOffice.builder().title(movie.getTitle()).posterLink(posterLink).synopsis(synopsis).build();
                boxOfficeRepository.save(boxOffice);//날짜


                try {
                    String[] stills = KobisScrapper
                            .getImageUrlsByCode(movie.getCode(), KobisScrapper.ImageType.STILL_CUT, true);
                          for(int i=0; i< stills.length;i++){
                              boxStillImageRepository.save(BoxStillImage.builder().imageLink(stills[i]).boxOfficeId(boxOffice).build());
                              if(i > 8) break;  //10개까지만 받음
                          }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    KobisScrapper.getActorList(movie.getCode()).forEach(actor -> {
                        actorRepository.save(ActorEntity.builder().boxOfficeId(boxOffice).actorName(actor.getActorName()).actorRole(actor.getCharacterName()).build());
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        });
    }

    public BoxOfficeDTO getSerachMovie(String title){   //자세히보기
           BoxOffice boxOffice =boxOfficeRepository.getSerachMovie(title);

             if(boxOffice != null){
                 return   BoxOfficeDTO.builder().title(boxOffice.getTitle())
                         .postLink(boxOffice.getPosterLink())
                         .stillImage(boxOffice.getStillImage().stream().map(boxStillImage -> boxStillImage.getImageLink()).collect(Collectors.toList()))
                         .synopsis(boxOffice.getSynopsis())
                         .actorList(boxOffice.getActorList().stream().map(actorEntity -> ActorDTO.builder().actorRole(actorEntity.getActorRole())
                                   .actorName(actorEntity.getActorName()).build()).collect(Collectors.toList())).build();
            }else{
                System.out.println("없는 값을 입력함");
                return BoxOfficeDTO.builder().build();
            }

    }

    @Transactional
    public List<BoxOfficeDTO> getSearchMovieList(String title){ //제목이 있으면 그거와 비슷한 요소들 없으면 전체
            List<BoxOfficeDTO> dtoList = new ArrayList<>();
            if(title != null){
                    boxOfficeRepository.getMovieList(title).forEach(boxOffice -> {
                        dtoList.add(BoxOfficeDTO.builder()
                                        .title(boxOffice.getTitle())
                                        .synopsis(boxOffice.getSynopsis())
                                        .postLink(boxOffice.getPosterLink())
                                        .actorList(boxOffice.getActorList().stream().map(actorEntity ->
                                                ActorDTO.builder().actorName(actorEntity.getActorName())
                                                .actorRole(actorEntity.getActorRole()).build()).collect(Collectors.toList()))
                                .build());
                    });
            }else{
                boxOfficeRepository.findAll().forEach(boxOffice -> {
                    dtoList.add(BoxOfficeDTO.builder()
                            .title(boxOffice.getTitle())
                            .synopsis(boxOffice.getSynopsis())
                            .postLink(boxOffice.getPosterLink())
                            .actorList(boxOffice.getActorList().stream().map(actorEntity ->
                                    ActorDTO.builder().actorName(actorEntity.getActorName())
                                            .actorRole(actorEntity.getActorRole()).build()).collect(Collectors.toList()))
                            .build());
                });
            }
            return dtoList;
    }

    public List<BoxOfficeDTO> getLikeMovie(){
        List<BoxOfficeDTO> dtoList = new ArrayList<>();
        boxOfficeRepository
                .getLikeMovieList(PageRequest.of(0,7))
                .forEach(boxOffice -> {
                    dtoList.add(BoxOfficeDTO.builder()
                                    .title(boxOffice.getTitle())
                                    .postLink(boxOffice.getPosterLink())
                            .build());
        });

        return dtoList;
    }


}
