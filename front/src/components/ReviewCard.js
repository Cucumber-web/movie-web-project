import {useState} from 'react';
import styled from "styled-components";
import {BiDotsVerticalRounded} from 'react-icons/bi';

const ReviewCard = ({spoiler,content}) => {
  const [isSpoiler, setIsSpoiler] = useState(spoiler)

  const handleSpoiler = () => {
    setIsSpoiler(false);
  }
  return (
    <CardWrapper>
      <CardTitle>
        <BiDotsVerticalRounded/>
      </CardTitle>
      <h1>
        {content}
      </h1>
      {isSpoiler && 
      <Spoiler>
        <h2>해당 리뷰에는 <strong>스포일러</strong>가 포함되어 있습니다</h2>
        <h2>그래도 확인하시겠습니까?</h2>
        <MoreBtn onClick={handleSpoiler}>더보기</MoreBtn>
      </Spoiler>}
    </CardWrapper>
  )
}

export default ReviewCard;

const CardWrapper = styled.div`
  position:relative;
  display:flex;
  justify-content: flex-start;
  flex-direction:column;
  align-items:center;
  width: 90%;
  height: 10rem;
  border-radius: 1rem;
  margin-bottom: 1rem;
  background-color: white;
`

const Spoiler = styled.div`
    position: absolute;
    display:flex;
    align-items:center;
    justify-content:center;
    flex-direction:column;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    backdrop-filter: blur(5px);
    border-radius: 1rem;

    strong {
      font-weight: 700;
    }
`;

const MoreBtn = styled.button`
  width: 3rem;
  height: 2rem;
  margin-top: 1rem;
  border-radius: 0.5rem;
  color: white;
  background-color: #03af59;
`
const CardTitle = styled.div`
  display:flex;
  justify-content:flex-end;
  align-items:center;
  width: 100%;
  height:2rem;
`