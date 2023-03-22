import React from "react";

import { Card, Grid, Link } from "@mui/material";
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";
import RemoveRedEye from "@mui/icons-material/RemoveRedEye";
import { blue, purple } from "@mui/material/colors";

import { getYear } from "../../utils/date";
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
        src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
        alt={movie.title}
      />
      <div className="movie-card-content">
        <h3>{movie.title}</h3>
        <ul className="movie-card-content-list">
          <li>
            {getYear(movie.release_date)}
          </li>
          <li>
            {movie.vote_average}
          </li>
          
        </ul>
      </div>

      <Grid container spacing={2}>
        <Grid item xs={12}>
          <div className="movie-card-layer">
            <Link
              href="/movies/1"
              underline="none"
              sx={{
                color: "#fff",

                width: "70%",
                fontSize: "15px",
                backgroundColor: blue[700],
                padding: "8px",
                borderRadius: "5px",
                transition: "all 0.3s ease-in-out",
                textAlign: "center",

                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                gap: "5px",
                ["&:hover"]: {
                  backgroundColor: blue[500],
                },
              }}
            >
              <RemoveRedEye sx={{ fontSize: 20 }} />
              View
            </Link>
            <Link
              href="/movies/1"
              underline="none"
              sx={{
                fontSize: "15px",
                width: "70%",
                color: "#fff",
                backgroundColor: purple[700],
                padding: "8px",
                borderRadius: "5px",
                textAlign: "center",
                transition: "all 0.3s ease-in-out",

                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                gap: "5px",
                ["&:hover"]: {
                  backgroundColor: purple[500],
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
