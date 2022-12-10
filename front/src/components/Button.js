import styled from "styled-components"
import { IoIosArrowForward, IoIosArrowBack } from "react-icons/io";

const NextArrow = () => {
  return (
    <Arrow>
      <IoIosArrowForward />
    </Arrow>
  )
}

const PrevArrow = () => {
  return (
    <ArrowPrev>
      <IoIosArrowBack />
    </ArrowPrev>
  )
}

export {NextArrow, PrevArrow};

const Arrow = styled.button`
    position: absolute !important;
    display: flex !important;
    justify-content: center !important;
    align-items: center !important;
    right: 3% !important;
    top: 50% !important;
    width: 2.5rem !important;
    height: 2.5rem !important;
    cursor: pointer !important;
    z-index: 10 !important;
    color: white !important;
    border: 1px solid white !important;
    border-radius: 50% !important;

    svg {
      width: 1rem;
      height: 1rem;
    }

    :hover {
        color: #03af59 !important;
        border: 1px solid #03af59 !important;
    }
`;

const ArrowPrev = styled.button`
    position: absolute !important;
    display: flex !important;
    justify-content: center !important;
    align-items: center !important;
    left: 3% !important;
    top: 50% !important;
    width: 2.5rem !important;
    height: 2.5rem !important;
    cursor: pointer !important;
    z-index: 10 !important;
    color: white !important;
    border: 1px solid white !important;
    border-radius: 50% !important;

    svg {
      width: 1rem;
      height: 1rem;
    }

    :hover {
        color: #03af59 !important;
        border: 1px solid #03af59 !important;
    }
`;