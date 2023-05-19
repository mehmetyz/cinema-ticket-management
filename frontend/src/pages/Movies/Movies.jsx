import React from "react";

import Section from "../../components/Section";

import {
  getMovieCount,
  getMovieCountByQuery,
  getMovies,
  search,
} from "../../api/movie";
import { useQueryParams } from "../../hook";

import MovieList from "../../components/MovieList";
import { Typography } from "@mui/material";

const MOVIE_PER_PAGE = 50;

const Movies = () => {
  const { genre, query } = useQueryParams();

  const [movies, setMovies] = React.useState([]);
  const [count, setCount] = React.useState(0);
  const [page, setPage] = React.useState(1);

  React.useEffect(() => {
    const fetchMovies = async () => {
      let movies, count;

      if (query) {
        movies = await search({ query, page, size: MOVIE_PER_PAGE });
        count = await getMovieCountByQuery(query);
      } else {
        movies = await getMovies({
          genreId: genre || 0,
          page,
          size: MOVIE_PER_PAGE,
        });
        count = await getMovieCount(genre || 0);
      }

      setCount(count);
      setMovies(movies);
    };
    fetchMovies();

    return () => {
      setMovies([]);
    };
  }, [genre, page, query]);

  return (
    <Section height="100vh" bgImage="/bg.jpg" opacity={0.6}>
      {movies && movies.length > 0 && count > 0 ? (
        <MovieList
          movies={movies}
          pagination={{ page, count, setPage }}
          props={{
            p: 2,
            pt: 10,
          }}
        />
      ) : (
        <Typography
          variant="h3"
          color="white"
          sx={{
            fontFamily: "'Poppins', sans-serif",
            fontWeight: 600,
            color: "#fff",
            maxWidth: "80%",
          }}
        >
          No movies found
        </Typography>
      )}
    </Section>
  );
};

export default Movies;
