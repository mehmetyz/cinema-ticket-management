import React, { useEffect } from "react";
import { useParams } from "react-router-dom";

import { Button, Grid, Typography } from "@mui/material";
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";
import FavoriteIcon from "@mui/icons-material/Favorite";

import { getMovie } from "../../api/movie";
import Section from "../../components/Section";

import "./MovieDetails.css";
import { deepOrange, purple } from "@mui/material/colors";
import { useApplication } from "../../context";

const MovieDetails = () => {
  const path = useParams();
  const [movie, setMovie] = React.useState(null);

  useEffect(() => {
    const fetchMovie = async () => {
      const movie = await getMovie(path.id);
      setMovie(movie);
    };

    fetchMovie();
  }, [path.id]);

  return (
    <Section
      height="100vh"
      bgImage={
        movie?.backdropPath
          ? `https://image.tmdb.org/t/p/original${movie.backdropPath}`
          : `../no-image.jpg`
      }
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
            src={
              movie?.posterPath
                ? `https://image.tmdb.org/t/p/original${movie.posterPath}`
                : `../no-image.jpg`
            }
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

          <Grid display="flex" gap={2} pt={3}>
            <Button
              variant="contained"
              sx={{
                fontFamily: "'Poppins', sans-serif",
                fontWeight: 600,
                backgroundColor: purple[500],
                maxWidth: "80%",
                ["&:hover"]: {
                  backgroundColor: purple[700],
                },
              }}
              href={useApplication().isAuth ? "/checkout" : "/login"}
            >
              <ConfirmationNumberIcon sx={{ mr: 1 }} />
              Buy Ticket
            </Button>
            <Button
              variant="contained"
              sx={{
                fontFamily: "'Poppins', sans-serif",
                fontWeight: 600,
                backgroundColor: deepOrange[500],
                maxWidth: "80%",
                ["&:hover"]: {
                  backgroundColor: deepOrange[700],
                },
              }}
            >
              <FavoriteIcon sx={{ mr: 1 }} />
              Add Favorites
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </Section>
  );
};

export default MovieDetails;
