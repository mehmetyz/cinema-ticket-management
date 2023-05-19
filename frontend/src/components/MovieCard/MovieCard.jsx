import React from "react";

import { Card, Grid, IconButton, Link } from "@mui/material";
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";
import RemoveRedEye from "@mui/icons-material/RemoveRedEye";
import FavoriteIcon from "@mui/icons-material/Favorite";
import { blue, purple, deepOrange } from "@mui/material/colors";

import { convertDate, getYear } from "../../utils/date";
import "./MovieCard.css";

const MovieCard = ({ movie }) => {
  return (
    <Card
      sx={{
        maxWidth: "188px",
        height: "400px",
        backgroundColor: "#171d22",
        ["&:hover"]: {
          transform: "scale(1.1)",
          transition: "all 0.3s ease-in-out",
          cursor: "pointer",
          ["--layer-display"]: "flex",
        },
      }}
    >
      <img
        className="movie-card-image"
        src={movie.posterPath ? `https://image.tmdb.org/t/p/w500${movie.posterPath}` : "no-image.jpg"}
        alt={movie.title}
      />
      <div className="movie-card-content">
        <h3>{movie.title}</h3>
        <ul className="movie-card-content-list">
          <li>{convertDate(movie.releaseDate).getFullYear()}</li>
          <li>{movie.rating.toFixed(1)}</li>
          <li>
            {movie.language.toUpperCase()}
          </li>
        </ul>
      </div>

      <Grid container spacing={2}>
        ,
        <Grid item xs={12}>
          <div className="movie-card-layer">
            <IconButton
              sx={{
                color: "#fff",
                position: "absolute",
                top: "20px",
                right: "10px",
                justifyContent: "center",
                backgroundColor: deepOrange[500],
                height: "30px",
                width: "30px",

                ["&:hover"]: {
                  backgroundColor: deepOrange[700],
                },
              }}
            >
              <FavoriteIcon sx={{ fontSize: 20 }} />
            </IconButton>

            <Link
              href={`/movies/${movie.movieId}`}
              underline="none"
              sx={{
                color: "#fff",

                width: "70%",
                fontSize: "15px",
                backgroundColor: blue[500],
                padding: "8px",
                borderRadius: "5px",
                transition: "all 0.3s ease-in-out",
                textAlign: "center",

                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                gap: "5px",
                ["&:hover"]: {
                  backgroundColor: blue[700],
                },
              }}
            >
              <RemoveRedEye sx={{ fontSize: 20 }} />
              View
            </Link>
            <Link
              href={`/movies/${movie.movieId}/checkout`}
              underline="none"
              sx={{
                fontSize: "15px",
                width: "70%",
                color: "#fff",
                backgroundColor: purple[500],
                padding: "8px",
                borderRadius: "5px",
                textAlign: "center",
                transition: "all 0.3s ease-in-out",

                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                gap: "5px",
                ["&:hover"]: {
                  backgroundColor: purple[700],
                },
              }}
            >
              <ConfirmationNumberIcon sx={{ fontSize: 20 }} />
              Buy Ticket
            </Link>
          </div>
        </Grid>
      </Grid>
    </Card>
  );
};

export default MovieCard;
