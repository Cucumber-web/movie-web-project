package com.movie.back.service;


import com.movie.back.data.cdata.Actor;
import com.movie.back.dto.ActorDTO;
import com.movie.back.dto.BoxOfficeDTO;
import com.movie.back.entity.ActorEntity;
import com.movie.back.entity.BoxOffice;
import com.movie.back.entity.BoxStillImage;
import com.movie.back.repository.ActorRepository;
import com.movie.back.repository.BoxOfficeRepository;
import com.movie.back.repository.BoxStillImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                List<ActorEntity> entityList =  boxOffice.getActorList().stream().map(actor -> ActorEntity
                        .builder()
                        .actorName(actor.getActorName())
                        .actorRole(actor.getActorRole())
                        .build()).collect(Collectors.toList());

                dtoList.add(BoxOfficeDTO.builder()
                                .title(boxOffice.getTitle())
                                .rank(boxOffice.getRanking())
                                .postLink(boxOffice.getPosterLink())
                                .stillImage(boxOffice.getStillImage().stream()
                                        .map(boxStillImage -> boxStillImage.getImageLink())
                                        .collect(Collectors.toList()))
                                .date(boxOffice.getDate())
                                .actorList(entityList.stream().map(actorEntity -> ActorDTO.builder()
                                        .actorName(actorEntity.getActorName())
                                        .actorRole(actorEntity.getActorRole())
                                        .build()).collect(Collectors.toList()))
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


}
