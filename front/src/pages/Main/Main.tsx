import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { Carousel, CarouselItem } from 'react-round-carousel';
import 'react-round-carousel/src/index.css';
import { Carousel as ThreeCarousel } from '3d-react-carousal';
import {
  setRefreshToken,
  getCookieToken,
  getAccessToken,
  setAccessToken,
} from '../../storage/Cookie';
import { useSelector, useDispatch } from 'react-redux';
import axios from 'axios';

interface MovieData {
  postLink: string;
  title: string;
  rating: string;
}

type postData = {
  postLink: string;
  stillImage: string[];
};

const Main = () => {
  const [movie, setMovie] = useState([]);
  const accessToken = getAccessToken();
  const refreshToken = getCookieToken();

  useEffect(() => {
    axios
      .get('/mvi/box', {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then(res => {
        setMovie(res.data);
        console.log(res.data);
      })
      .catch(err => {
        if (err.response.data.msg === 'Expired Token') {
          axios
            .post('/refreshToken', {
              accessToken: accessToken,
              refreshToken: refreshToken,
            })
            .then(res => {
              setRefreshToken(res.data.refreshToken);
              setAccessToken(res.data.accessToken);
              document.location.reload();
            });
        }
      });
  }, []);

  const threeCarousel = movie?.map((props: postData, index) => (
    <MainPoster>
      <img src={props.postLink} key={index} />
      <PosterStillCut style={{ border: '1px solid black !important' }}>
        {props?.stillImage.slice(0, 9).map((props, index) => (
          <img src={props} alt={props} key={index} />
        ))}
      </PosterStillCut>
    </MainPoster>
  ));
  const movieTest: CarouselItem[] = movie?.map(
    (props: MovieData, index: number) => ({
      alt: 'movie poster',
      image: props.postLink,
      content: (
        <Link
          key={index}
          to={'/detail'}
          state={{ image: props?.postLink, title: props?.title }}
        >
          <MovieTitle>{props.title}</MovieTitle>
          <MovieTitle>{props.rating}</MovieTitle>
        </Link>
      ),
    })
  );

  return (
    <>
      <MainWrapper>
        <MainTopText>이번주 Best 영화</MainTopText>
        <MovieListWrapper>
          <ThreeCarousel slides={threeCarousel} autoplay={false} />
        </MovieListWrapper>
      </MainWrapper>
      <QuizWrapper>
        <MainTopText>요즘 인기있는 영화 퀴즈</MainTopText>
        <QuizImgWrapper>
          {movie.slice(0, 6).map((props: postData, idx) => (
            <img src={props.postLink} key={idx} />
          ))}
        </QuizImgWrapper>
      </QuizWrapper>
      <CarouselWrapper>
        <MainTopText>이번주 상영하는 영화</MainTopText>
        <Carousel items={movieTest} itemWidth={500} />
      </CarouselWrapper>
    </>
  );
};

export default Main;

const MainWrapper = styled.section`
  width: 100%;
`;

const MainTopText = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 7vh;
  font-size: 1.3rem;
  font-weight: 600;
  color: #03af59;
`;

const MovieListWrapper = styled.div`
  width: 100%;
  height: 40vh;
  * {
    box-shadow: none !important;
  }

  /* div div div div div div {
    border: 1px solid white !important;
    border-radius: 50%;
  } */

  img {
    width: 25rem;
    height: 30rem;
  }
`;

const MovieTitle = styled.strong`
  width: 100%;
  margin: auto;
`;

const CarouselWrapper = styled.section`
  width: 100%;
  height: 50vh;
`;

const QuizWrapper = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 50vh;
  margin-top: 10rem;
`;

const QuizImgWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  img {
    width: 10%;
    height: 100%;
    padding-right: 0.5rem;
  }
`;

const MainPoster = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
`;

const PosterStillCut = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 20rem;
  height: 30rem;
  background-color: #fefefe;
  img {
    flex-grow: 1;
    width: 30%;
    height: 35%;
  }
`;
