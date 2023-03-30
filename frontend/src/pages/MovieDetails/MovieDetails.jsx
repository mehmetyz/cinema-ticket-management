import { Button, Grid, Typography } from "@mui/material";
import React from "react";
import { useParams } from "react-router-dom";
import getMovie from "../../api/movie";
import Section from "../../components/Section";

import "./MovieDetails.css";

const MovieDetails = () => {
  const path = useParams();
  const movie = getMovie().find((movie) => movie.id === parseInt(path.id));
  return (
    <Section
      height="100vh"
      bgImage={`https://image.tmdb.org/t/p/original${movie?.backdrop_path}`}
      opacity={0.3}
    >
      <Grid
        container
        spacing={5}
        pt={10}
        sx={{
          height: "100%",
        }}
      >
        <Grid item xs={12} md={3} display="flex" justifyContent="center">
          <img
            src={`https://image.tmdb.org/t/p/original${movie?.poster_path}`}
            alt={movie?.title}
            className="movie-details__poster"
          />
        </Grid>
        <Grid item xs={12} md={9}>
          <Typography
            variant="h2"
            pr={2}
            sx={{
              fontFamily: "'Poppins', sans-serif",
              fontWeight: 600,
              color: "#fff",
              maxWidth: "80%",
            }}
          >
            {movie?.title}
          </Typography>

          <Typography
            variant="h5"
            pr={2}
            pt={3}
            sx={{
              fontFamily: "'Poppins', sans-serif",
              fontWeight: 400,
              color: "#fff",
              textAlign: "justify",
              maxWidth: "80%",
            }}
          >
            {movie?.overview}
          </Typography>
        </Grid>

        <Grid item xs={12} md={3}>
          <Button variant="contained" color="primary">
            Watch Now
          </Button>
          <Button variant="contained" color="primary">
            Add to Watchlist
          </Button>
        </Grid>
      </Grid>
    </Section>
  );
};

export default MovieDetails;
