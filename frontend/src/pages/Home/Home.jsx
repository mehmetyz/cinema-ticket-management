import React, { useEffect } from "react";

import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";

import Footer from "../../components/Footer";
import PopularMovies from "../../components/PopularMovies";
import RandomMovie from "../../components/RandomMovie";

import { getMovies } from "../../api/movie";

import "./Home.css";

const Home = ({ genres }) => {
  const [randomMovie, setRandomMovie] = React.useState(null);
  const scrollBtn = React.useRef(null);

  useEffect(() => {
    const fetchRandomMovie = async () => {
      const randomMovie = await getMovies();
      setRandomMovie(randomMovie);
    };
    fetchRandomMovie();

    return () => {
      setRandomMovie(null);
    };
  }, []);

  useEffect(() => {
    window.addEventListener("scroll", () => {
      if (window.scrollY > 100) {
        scrollBtn.current.style.display = "block";
      } else {
        scrollBtn.current.style.display = "none";
      }
    });

    return () => {
      window.removeEventListener("scroll", () => {});
    };
  }, []);

  return (
    <>
      <RandomMovie movie={randomMovie} genres={genres} />
      <PopularMovies genres={genres} />
      <Footer />

      <button
        className="scroll-top scroll-to-target"
        data-target="html"
        ref={scrollBtn}
        onClick={() => {
          window.scrollTo({ top: 0, behavior: "smooth" });
        }}
      >
        <KeyboardArrowUpIcon />
      </button>
    </>
  );
};

export default Home;
