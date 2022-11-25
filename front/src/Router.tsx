import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './pages/Login/Login';
import Navigation from './components/Nav/Navigation';
import Main from './pages/Main/Main';
// import Detail from './pages/Details/Detail';
import SignUp from './pages/Login/SignUp';

const Router = () => {
  return (
    <>
      <BrowserRouter>
        <Navigation />
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/login/*" element={<Login />} />
          <Route path="/signup/*" element={<SignUp />} />
          {/* <Route path="/detail" element={<Detail />} /> */}
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;
