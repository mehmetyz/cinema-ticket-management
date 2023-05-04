import React, { useEffect } from "react";

import { Container, Grid, Typography, Button, Link } from "@mui/material";
import { blue } from "@mui/material/colors";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

import Section from "../Section";
import MovieCard from "../MovieCard";
import { getMovieCount } from "../../utils/resize";
import { getMovies } from "../../api/movie";
import MovieList from "../MovieList";

const styles = {
  primary: {
    color: blue[300],
  },
};

const PopularMovies = ({ genres }) => {
  const [movies, setMovies] = React.useState([]);
  const [filter, setFilter] = React.useState({ genre: 0 });

  useEffect(() => {
    const setMovies = async () => {
      let currentMovies = await getMovies();
      if (filter && Object.keys(filter).length > 0) {
        Object.keys(filter).forEach((key) => {
          currentMovies = currentMovies.filter((movie) => {
            if (key === "genre") {
              if (filter[key] === 0) {
                return (
                  movie?.rating > 200
                );
              }

              return movie.genre.filter((genre) => genre.name === filter[key])
            }
            return movie[key] === filter[key];
          });
        });
      }
      currentMovies = currentMovies.sort((a, b) =>
        parseFloat(b.vote_average) - parseFloat(a.vote_average) > 0 ? 1 : -1
      );
      setMovies(currentMovies);
    };
    setMovies();
  }, [filter]);

  useEffect(() => {
    const resize = () => {
      const count = getMovieCount();
      setFilter({ count });
    };
    window.addEventListener("resize", resize);

    return () => {
      window.removeEventListener("resize", resize);
    };
  }, []);

  return (
    <Section id="movies" height="200vh" bgImage="/bg.jpg" opacity={0.6}>
      <Container maxWidth="m" sx={{ height: "100%", padding: "100px 0 0 0" }}>
        <Typography
          variant="h6"
          component="h6"
          sx={{
            color: styles.primary.color,
            fontSize: "14px",
            fontWeight: "500",
            letterSpacing: "1px",
          }}
          mb={1}
        >
          MOVIES
        </Typography>

        <Typography
          variant="h6"
          component="h6"
          sx={{
            color: "#fff",
            fontSize: "36px",
            fontWeight: "700",
            letterSpacing: "1px",
          }}
          mb={1}
        >
          Most Popular Movies
        </Typography>

        <Grid
          container
          sx={{
            width: "100%",
            height: "50px",
            alignItems: "center",
            justifyContent: "center",
            rowGap: "10px",
          }}
          mb={10}
          mt={10}
        >
          {genres.map((genre) => (
            <Grid key={genre.id} item mr={2}>
              <Button
                underline="none"
                sx={{
                  color: "#fff",
                  backgroundColor:
                    filter.genre === genre.id && styles.primary.color,
                  fontSize: "14px",
                  fontWeight: "600",
                  letterSpacing: "1px",
                  transition: "all 0.3s ease-in-out",
                  border: "2px solid " + styles.primary.color,
                  borderRadius: "5px",
                  padding: "10px",
                  ["&:hover"]: {
                    color: styles.primary.color,
                  },
                }}
                onClick={(e) => {
                  e.preventDefault();
                  setFilter({ genre: genre.id });
                }}
              >
                {genre.name}
              </Button>
            </Grid>
          ))}
        </Grid>
        <MovieList
          movies={movies}
          pagination={{ count: getMovieCount() }}
          genre={filter.genre}
        />
      </Container>
    </Section>
  );
};

export default PopularMovies;
