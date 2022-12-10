import React from "react";
import { useLocation } from "react-router-dom";
import styled from "styled-components";
import { useState, useEffect } from "react";
import {
    getAccessToken
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
                        <MovieInfo>
                            <TitleLike>
                                <Title>{movieTitle}</Title>
                                <Line/>
                                <LikeBtn />
                            </TitleLike>
                        </MovieInfo>
                    </DetailWrapper>
                </>
            )}
        </div>
    );
};

export default Detail;

const DetailWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100vw;
    height: 80vh;
    border: 1px solid white;
`;

const ImageWrapper = styled.img`
    width: 25rem;
    height: 33rem;
`;

const MovieInfo = styled.div`
    width: 25rem;
    height: 33rem;
    border: 1px solid white;
`
const TitleLike = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
`

const LikeBtn = styled.div`
    width: 5rem;
    height: 2.5rem;
    border: 1px solid white;
`

const Title = styled.h2`
    font-size: 1.7rem;
    font-weight: 700;
    color: white;
`
const Line = styled.div`
    width:1px;
    height: 2.5rem;
    border: 1px solid white;
`