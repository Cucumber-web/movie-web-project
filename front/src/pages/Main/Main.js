import React from "react";
import styled from "styled-components";
import { useEffect, useState } from "react";
import { Planet } from "react-planet";
import { AiOutlineArrowLeft, AiOutlineArrowRight } from "react-icons/ai";
import {
    setRefreshToken,
    getCookieToken,
    getAccessToken,
    setAccessToken,
} from "../../storage/Cookie";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const Main = () => {
    const [movie, setMovie] = useState([]);
    const accessToken = getAccessToken();
    const refreshToken = getCookieToken();
    const [carouselRotation, setCarouselRotation] = useState(0);
    const navigate = useNavigate();

    const handleRotationPlus = () => {
        setCarouselRotation((prev) => prev + 40);
    };

    const handleRotationMinus = () => {
        setCarouselRotation((prev) => prev - 40);
    };
    console.log(movie);
    useEffect(() => {
        axios
            .get("/mvi/box")
            .then((res) => {
                setMovie(res.data);
            })
            .catch((err) => {
                if (err.response.data.msg === "Expired Token") {
                    axios
                        .post("/refreshToken", {
                            accessToken: accessToken,
                            refreshToken: refreshToken,
                        })
                        .then((res) => {
                            setRefreshToken(res.data.refreshToken);
                            setAccessToken(res.data.accessToken);
                            document.location.reload();
                        });
                }
            });
    }, []);

    return (
        <MainOutHeightWrapper>
            <MainWrapper>
                <p onClick={() => navigate("/login")}>asdkjvbasdjkvbasdhjkvb</p>
            </MainWrapper>
            <QuizWrapper>
                <MainTopText>요즘 인기있는 영화 퀴즈</MainTopText>
                <QuizImgWrapper>
                    {movie &&
                        movie
                            .slice(0, 7)
                            .map((props, idx) => (
                                <img
                                    src={props.postLink}
                                    key={idx + "movieQuiz"}
                                    alt="quiz imag"
                                    onClick={() =>
                                        navigate("/detail", {
                                            state: { "title": props.title },
                                        })
                                    }
                                />
                            ))}
                </QuizImgWrapper>
            </QuizWrapper>
            <ThisWeek>
                <h2>이번 주에 상영하는 영화</h2>
                <div>
                    <WeekBtn onClick={handleRotationMinus}>
                        <AiOutlineArrowLeft />
                    </WeekBtn>
                    <WeekBtn onClick={handleRotationPlus}>
                        <AiOutlineArrowRight />
                    </WeekBtn>
                </div>
            </ThisWeek>
            <CarouselWrapperCir>
                <Planet
                    orbitStyle={(defaultStyle) => ({
                        ...defaultStyle,
                        borderWidth: 4,
                        borderStyle: "solid",
                        borderColor: "#03af59",
                    })}
                    open
                    orbitRadius={450}
                    rotation={carouselRotation}
                    bounce={false}
                    tension={50}
                >
                    {movie.slice(0, 9).map((props, idx) => (
                        <div key={idx + "under carousel poster"}>
                            <PostImageWrapper
                                src={props?.postLink}
                                alt="post"
                            />
                        </div>
                    ))}
                </Planet>
            </CarouselWrapperCir>
        </MainOutHeightWrapper>
    );
};

export default Main;

const MainWrapper = styled.section`
    width: 100vw;
    height: 80vh;
`;

const MainOutHeightWrapper = styled.section`
    position: relative;
    height: 200vh;
    overflow-y: hidden;
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

const PostImageWrapper = styled.img`
    width: 12rem;
    height: 15rem;
`;

const MovieListWrapper = styled.div`
    width: 100%;
    height: 40vh;
    * {
        box-shadow: none !important;
    }

    div div div div div div {
        border: none !important;
    }

    div div div div div div .fa {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 2rem;
        height: 2rem;
        border: 1px solid white !important;
        border-radius: 50%;
    }

    img {
        width: 26rem;
        height: 31rem;
    }
`;

const QuizWrapper = styled.section`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 40vh;
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

const CarouselWrapperCir = styled.section`
    position: absolute;
    bottom: -20rem;
    display: flex;
    justify-content: center;
    width: 100%;
    height: 30vh;
`;

const ThisWeek = styled.div`
    display: flex;
    justify-content: flex-start;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 8rem;

    h2 {
        font-size: 1.3rem;
        font-weight: 600;
        color: #03af59;
    }
    div {
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 1rem;
    }
`;

const WeekBtn = styled.button`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 3rem;
    height: 3rem;
    border: 1px solid #03af59;
    border-radius: 50%;

    & + & {
        margin-left: 2rem;
    }
    svg {
        width: 2rem;
        height: 2rem;
        color: #03af59;
    }
`;
