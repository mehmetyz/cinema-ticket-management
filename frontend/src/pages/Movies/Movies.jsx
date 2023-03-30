import React from "react";

import Section from "../../components/Section";

import getMovie from "../../api/movie";
import { useQueryParams } from "../../hook";
import { getMovieCount } from "../../utils/resize";
import { textSearch } from "../../utils/string";
import MovieList from "../../components/MovieList";
import { Typography } from "@mui/material";

const Movies = () => {
  const { id, genre, search } = useQueryParams();
  const [movies, setMovies] = React.useState([]);
  const [page, setPage] = React.useState(1);

  React.useEffect(() => {
    const genre_id = genre ? parseInt(genre) : 0;
    const currentMovies = getMovie().filter((movie) => {
      const isGenre = genre_id === 0 || movie.genre_ids.includes(genre_id);
      const isSearch = search ? textSearch(movie.title, search) : true;
      const isId = id ? movie.id === parseInt(id) : true;

      return isGenre && isSearch && isId;
    });
    setMovies(currentMovies);
  }, [id, genre, search]);

  return (
    <Section height="100vh" bgImage="/bg.jpg" opacity={0.6}>
      {movies && movies.length ? (
        <MovieList
          movies={movies}
          pagination={{ page, count: getMovieCount(), setPage }}
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
