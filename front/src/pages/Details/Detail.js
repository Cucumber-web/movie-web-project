import React from "react";
import { useLocation } from "react-router-dom";
import styled from "styled-components";
import { useState, useEffect } from "react";
import Like from "../../components/Nav/Like";
import axios from "axios";

const Detail = () => {
    const location = useLocation();
    const [movieData, setMovieData] = useState({});
    const movieTitle = location.state;
    console.log(location.state);
    useEffect(() => {
        axios
            .get(`/mvi/read?title=${movieTitle}`)
            .then((res) => {
                console.log(res);
                setMovieData(res.data);
            })
            .catch((err) => console.log(err));
    }, []);
    return (
        <div>
            {movieData && (
                <>
                    <h1>{movieTitle}</h1>
                    <ImageWrapper src={movieData.postLink} />
                    {movieData.stillImage &&
                        movieData.stillImage
                            .slice(0, 9)
                            .map((props) => (
                                <ImageWrapper src={props} alt="still image" />
                            ))}
                    <p>{movieData.synopsis}</p>
                    <Like />
                </>
            )}
        </div>
    );
};

export default Detail;

const ImageWrapper = styled.img`
    width: 200px;
    height: 400px;
`;
