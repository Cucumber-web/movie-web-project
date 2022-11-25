import styled, { css } from 'styled-components';
import { BiUser } from 'react-icons/bi';
import { HiMagnifyingGlass } from 'react-icons/hi2';
import { useLocation, Link } from 'react-router-dom';
import { useState, useEffect } from 'react';

const Navigation = () => {
  const location = useLocation();
  const [path] = useState(location.pathname);
  const [navHidden, setNavHidden] = useState(false);

  useEffect(() => {
    if (path === '/login' || path === '/signup') {
      setNavHidden(true);
    }
  }, [location]);

  return (
    <NavWrapper>
      <h1>Logo</h1>
      <NavSearchWrapper hidden={navHidden}>
        <NavSearchIcon />
        <NavSearch />
      </NavSearchWrapper>
      <NavUserIcon />
    </NavWrapper>
  );
};

export default Navigation;

const NavWrapper = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100vw;
  height: 10vh;
  h1 {
    color: #03af59;
    font-size: 2rem;
  }
`;

const NavSearchWrapper = styled.div`
  ${props =>
    props.hidden &&
    css`
      visibility: hidden;
    `}
  position: relative;
  display: flex;
  align-items: center;
  width: 25%;
  height: 100%;
  margin-right: 1rem;
`;

const NavSearch = styled.input`
  width: 100%;
  height: 40%;
  padding-left: 2.5rem;
  border: 1px solid #03af59;
  border-radius: 1rem;
`;

const NavSearchIcon = styled(HiMagnifyingGlass)`
  position: absolute;
  left: 6%;
  color: #03af59;
`;

const NavUserIcon = styled(BiUser)`
  font-size: 2rem;
  height: 40%;
  color: #03af59;
`;

// const NavUserIcon = styled(BiUser)`
//   height: 100%;
//   cursor: pointer;
// `;
