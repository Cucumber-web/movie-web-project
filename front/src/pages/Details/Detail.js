import React from "react";
import { useLocation } from "react-router-dom";
import styled from "styled-components";
import { useState, useEffect } from "react";
import {
    getAccessToken,
    setAccessToken,
    getCookieToken,
    setRefreshToken,
} from "../../storage/Cookie";
import axios from "axios";
import { newAccessToken } from "../../module/refreshToken";
axios.defaults.headers.common["Authorization"] = getAccessToken();

const Detail = () => {
    const location = useLocation();
    const [movieData, setMovieData] = useState({});
    const [isLike, setIsLike] = useState(false);
    const [myMovie, setMyMovie] = useState(false);
    const movieTitle = location.state;

    const readConfig = {
        method: "get",
        url: `/like/read?title=${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    useEffect(() => {
        axios
            .get(`/mvi/read?title=${movieTitle}`)
            .then((res) => {
                console.log(res);
                setMovieData(res.data);
                axios(readConfig)
                    .then((res) => {
                        console.log(res);
                        setIsLike(res.data);
                    })
                    .catch((err) => {
                        newAccessToken(err);
                    });
            })
            .catch((err) => console.log(err));
    }, []);

    const LikeConfig = {
        method: "post",
        url: `/like/${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const deleteLikeConfig = {
        method: "delete",
        url: `/like/delete?title=${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const myMovieConfig = {
        method: "post",
        url: `/my/save/${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const deleteMyMovieConfig = {
        method: "delete",
        url: `/my/remove?title=${movieTitle}`,
        headers: {
            Authorization: `Bearer ${getAccessToken()}`,
        },
    };

    const handleLikeButton = () => {
        if (!isLike) {
            axios(LikeConfig)
                .then((res) => {
                    console.log(res);
                    setIsLike(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(LikeConfig)
                        .then((res) => {
                            console.log(res);
                            setIsLike(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        } else {
            axios(deleteLikeConfig)
                .then((res) => {
                    setIsLike(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(deleteLikeConfig)
                        .then((res) => {
                            console.log(res);
                            setIsLike(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        }
    };

    const handleMyMovieButton = () => {
        if (!myMovie) {
            axios(myMovieConfig)
                .then((res) => {
                    console.log(res);
                    setMyMovie(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(myMovieConfig)
                        .then((res) => {
                            console.log(res);
                            setMyMovie(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        } else {
            axios(deleteMyMovieConfig)
                .then((res) => {
                    setMyMovie(res.data);
                })
                .catch((err) => {
                    newAccessToken(err);
                    axios(deleteMyMovieConfig)
                        .then((res) => {
                            console.log(res);
                            setMyMovie(res.data);
                        })
                        .catch((err) => console.log(err));
                });
        }
    };

    return (
        <div>
            {movieData && (
                <>
                    <h1>{movieTitle}</h1>
                    <DetailWrapper>
                        <ImageWrapper src={movieData.postLink} />
                        <DetailInfoWrapper>
                            <DetailTitle>
                                {movieData.title}
                                <LikeBox
                                    isLike={isLike}
                                    onClick={handleLikeButton}
                                >
                                    좋아요
                                </LikeBox>
                                <LikeBox
                                    isLike={myMovie}
                                    onClick={handleMyMovieButton}
                                >
                                    찜 하기
                                </LikeBox>
                            </DetailTitle>
                            <DetailDesc>
                                <p>{movieData.synopsis}</p>
                            </DetailDesc>
                        </DetailInfoWrapper>
                        {/* {movieData.stillImage &&
                        movieData.stillImage
                            .slice(0, 9)
                            .map((props) => (
                                <ImageWrapper src={props} alt="still image" />
                            ))} */}
                    </DetailWrapper>
                </>
            )}
        </div>
    );
};

export default Detail;

const DetailWrapper = styled.div`
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 100vw;
    height: 80vh;
    border: 1px solid white;
`;

const DetailInfoWrapper = styled.div`
    display: flex;
    justify-content: space-around;
    flex-direction: column;
    width: 40%;
    height: 33rem;
`;

const ImageWrapper = styled.img`
    width: 25rem;
    height: 33rem;
`;

const DetailTitle = styled.h2`
    display: flex;
    justify-content: space-between;
    font-size: 1.5rem;
    color: white;
`;

const DetailDesc = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 12rem;
    padding: 1rem;
    background-color: rgba(205, 207, 204, 0.7);
`;

const LikeBox = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 5rem;
    height: 3rem;
    background-color: ${(props) => (props.isLike ? "red" : "rgba(0,0,0,0.3)")};
`;
