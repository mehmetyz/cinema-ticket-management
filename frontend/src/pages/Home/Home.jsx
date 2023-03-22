import React, { useEffect } from "react";

import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";

import Footer from "../../components/Footer";
import PopularMovies from "../../components/PopularMovies";
import RandomMovie from "../../components/RandomMovie";

import getMovie from "../../api/movie";

import "./Home.css";

const Home = ({ genres }) => {
  const [randomMovie, setRandomMovie] = React.useState(null);
  const scrollBtn = React.useRef(null);

  useEffect(() => {
    const rndMovies = getMovie()
      .filter(
        (movie) =>
          !movie.adult &&
          movie.vote_count > 200 &&
          !movie.genre_ids.includes(10749)
      )
      .sort((a, b) => Math.random() - 0.5)[0];

    setRandomMovie(rndMovies);

    const getYoutubeUrl = async (movieId) => {
      const url = `https://api.themoviedb.org/3/movie/${movieId}/videos?api_key=1834a4aeda26a79693e9cce465003ce6`;
      const response = await fetch(url);
      const data = await response.json();
      const youtubeUrl = data.results.find(
        (item) => item.site === "YouTube" && item.type === "Trailer"
      );
      setRandomMovie((prev) => ({
        ...prev,
        youtubeUrl: "https://www.youtube.com/embed/" + youtubeUrl.key,
      }));
    };

    getYoutubeUrl(rndMovies.id);
    setRandomMovie(rndMovies);

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
