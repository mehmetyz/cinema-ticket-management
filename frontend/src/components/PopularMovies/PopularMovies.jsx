import React, { useEffect } from "react";

import { Container, Grid, Typography, Button, Link } from "@mui/material";
import { blue } from "@mui/material/colors";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

import Section from "../Section";
import MovieCard from "../MovieCard";
import getMovieCount from "../../utils/resize";
import getMovie from "../../api/movie";

const styles = {
  primary: {
    color: blue[300],
  },
}

const PopularMovies = ({ genres }) => {
  const [movies, setMovies] = React.useState([]);
  const [filter, setFilter] = React.useState({ genre: 0 });

  useEffect(() => {
    let currentMovies = getMovie();
    if (filter && Object.keys(filter).length > 0) {
      Object.keys(filter).forEach((key) => {
        currentMovies = currentMovies.filter((movie) => {
          if (key === "genre") {
            if (filter[key] === 0) {
              return (
                !movie.genre_ids.includes(10749) &&
                !movie.adult &&
                movie.vote_count > 200
              );
            }

            return movie.genre_ids.includes(filter[key]);
          }
          return movie[key] === filter[key];
        });
      });
    }
    currentMovies = currentMovies.sort((a, b) =>
      parseFloat(b.vote_average) - parseFloat(a.vote_average) > 0 ? 1 : -1
    );
    setMovies(currentMovies);
  }, [filter]);

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
          md={12}
          mb={10}
          mt={10}
        >
          {genres.map((genre) => (
            <Grid key={genre.id} item mr={2}>
              <Button
                underline="none"
                sx={{
                  color: "#fff",
                  backgroundColor: filter.genre === genre.id && styles.primary.color,
                  fontSize: "14px",
                  fontWeight: "600",
                  letterSpacing: "1px",
                  transition: "all 0.3s ease-in-out",
                  border: "2px solid "+ styles.primary.color,
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
        <Grid
          container
          maxWidth="m"
          sx={{
            alignItems: "center",
            justifyContent: "center",
            width: "100%",
            heigth: "auto",
            maxHeight: "90%",
            display: "flex",
            flexWrap: "wrap",
            rowGap: "30px",
            columnGap: "15px",
            overflow: "auto",

            ["&::-webkit-scrollbar"]: {
              width: "0.4em",
            },
            ["&::-webkit-scrollbar-track"]: {
              boxShadow: "inset 0 0 6px rgba(0,0,0,0.00)",
              webkitBoxShadow: "inset 0 0 6px rgba(0,0,0,0.00)",
            },
            ["&::-webkit-scrollbar-thumb"]: {
              backgroundColor: "rgba(0,0,0,.1)",
              outline: "1px solid slategrey",
            },
          }}
        >
          {movies.slice(0, getMovieCount()).map((movie) => (
            <MovieCard movie={movie} key={movie.id} />
          ))}
        </Grid>

        <Grid
          container
          sx={{
            width: "100%",
            height: "50px",
            mt: 2,
            display: "flex",
            justifyContent: "flex-end",
          }}
          md={12}
        >
          <Link
            href={`/movies?genre=${filter.genre}`}
            underline="none"
            sx={{
              color: "#fff",
              fontSize: "15px",
              backgroundColor: blue[700],
              padding: "8px",
              borderRadius: "5px",
              transition: "all 0.3s ease-in-out",
              textAlign: "center",
              height: "max-content",
              gap: "5px",
              ["&:hover"]: {
                backgroundColor: blue[500],
              },

              display: "flex",
              alignItems: "center",
            }}
            mr={4}
            mt={4}
          >
            <ArrowForwardIosIcon sx={{ fontSize: "15px" }} />
            Load More
          </Link>
        </Grid>
      </Container>
    </Section>
  );
};

export default PopularMovies;
