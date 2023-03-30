import React from "react";
import { Routes, Route } from "react-router-dom";
import { produce } from "immer";

import NoPage from "./pages/NoPage";
import Authentication from "./pages/Authentication";
import Home from "./pages/Home";

import Header from "./components/Header";

import genre from "./api/genre";
import ApplicationContext from "./context";

import "./App.css";
import Movies from "./pages/Movies";
import { handleScroll } from "./utils/resize";
import MovieDetails from "./pages/MovieDetails";

function App() {
  const genres = genre();
  const [context, setContext] = React.useState({
    isAuth: false,
    isNavTransparent: true,
    login: () => {
      setContext(
        produce((draft) => {
          draft.isAuth = true;
        })
      );
    },
    logout: () => {
      setContext(
        produce((draft) => {
          draft.isAuth = false;
        })
      );
    },
    enableNavTransparent: (flag) => {
      setContext(
        produce((draft) => {
          draft.isNavTransparent = flag;
        })
      );
    },
  });

  React.useEffect(() => {
    window.addEventListener("scroll", () =>
      handleScroll(window.scrollY > 0, context)
    );
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <>
      <ApplicationContext.Provider value={context}>
        <Header movieGenres={genres}></Header>
        <Routes>
          <Route path="/" element={<Home genres={genres} />} exact />
          <Route path="/login" element={<Authentication login />} />
          <Route path="/register" element={<Authentication />} />
          <Route path="/movies" element={<Movies genres={genres} />} />
          <Route path="/movies/:id" element={<MovieDetails />} />
          <Route path="*" element={<NoPage />} />
        </Routes>
      </ApplicationContext.Provider>
    </>
  );
}
export default App;
