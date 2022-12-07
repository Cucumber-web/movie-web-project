import React from "react";
import { useLocation } from "react-router-dom";
import styled from "styled-components";
import { useState, useEffect } from "react";
import { getAccessToken } from "../../storage/Cookie";
import axios from "axios";
axios.defaults.headers.common['Authorization'] = getAccessToken();

const Detail = () => {
    const location = useLocation();
    const [movieData, setMovieData] = useState({});
    const movieTitle = location.state;
    console.log(location.state);
    console.log(getAccessToken());
    useEffect(() => {
        axios
            .get(`/mvi/read?title=${movieTitle}`)
            .then((res) => {
                console.log(res);
                setMovieData(res.data);
            })
            .catch((err) => console.log(err));
    }, []);

    const handleLikeButton = () => {
        axios
            .post(`/like/${movieTitle}`,{
                headers:{
                    Authorization: `Bearer ${getAccessToken()}`,
                }
            })
            .then((res) => console.log(res))
            .catch((err) => console.log(err));
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
                                <LikeBox onClick={handleLikeButton}>
                                    좋아요
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
    background-color: rgba(205, 207, 204, 0.7);
`;
