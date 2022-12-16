import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import styled from "styled-components";
import LinearProgress from '@mui/material/LinearProgress';
import Box from '@mui/material/Box';
import axios from "axios";
import { getAccessToken } from "../storage/Cookie";

const Quiz = () => {
  const location = useLocation();
  const title = location.state;
  const [quizStart, setQuizStart] = useState(false);
  const [current, setCurrent] = useState(0);

  const quizConfig = {
    method: 'get',
    url:`/mvi/problem?title=${title}`,
    header: {
      Authorization: `Bearer ${getAccessToken()}`
    }
  }

  useEffect(() => {
    axios(quizConfig).then(res => console.log(res)).catch(err => console.log(err))
  })

  const handleQuizStart = () => {
    setQuizStart(true);
  }
  return (
    <QuizWrapper>
    {!quizStart &&
      <>
        <MovieTitle><strong>{title}</strong>에 대한 퀴즈를 풀어보시겠습니까?</MovieTitle>
        <QuizBtn onClick={handleQuizStart}>시작하기</QuizBtn>
      </>
    }
    {quizStart &&
      <QuizListWrapper>
        <Box sx={{width: '20rem'}}>
          <LinearProgress variant="determinate" color="warning" value={current}/>
        </Box>
        <TestBtn onClick={() => setCurrent(prev => prev + 10)}/>
      </QuizListWrapper>
    }
    </QuizWrapper>
  )
}

export default Quiz;

const QuizWrapper = styled.section`
  display:flex;
  align-items:center;
  justify-content:center;
  flex-direction: column;
  width: 100vw;
  height: 90vh;
`

const MovieTitle = styled.h2`
  color:white;

  strong{
    font-weight: 700;
  }
`

const QuizBtn = styled.button`
  width: 10rem;
  height: 3rem;
  margin-top: 3rem;
  background-color:#03af59;
  color: white;
  border-radius: 0.5rem;
`
const QuizListWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content:center;
  flex-direction:column;
  width: 100%;
  height: 100%;
  
`

const TestBtn = styled.button`
  width: 5rem;
  height: 2rem;
  background-color:blue;
`