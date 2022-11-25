// import { useLocation } from 'react-router-dom';
// import styled from 'styled-components';
// import { useState, useEffect } from 'react';

// interface MovieData {
//   Result: object[];
// }

// const Detail = () => {
//   const location = useLocation();
//   const [movieData, setMovieData] = useState([]);
//   const movieTitle = location.state.title.substr(
//     3,
//     location.state.title.length - 7
//   );
//   useEffect(() => {
//     fetch(`/mvi/box/read?name=${movieTitle}`)
//       .then(res => res.json())
//       .then(data => setMovieData(data));
//   }, []);
//   const plots =
//     movieData?.Result && movieData?.Result[0].plots.plot[0].plotText;

//   return (
//     <div>
//       <h1>{movieTitle}</h1>
//       <ImageWrapper src={location.state.image} />
//       <p>{plots}</p>
//     </div>
//   );
// };

// export default Detail;

// const ImageWrapper = styled.img`
//   width: 200px;
//   height: 400px;
// `;
