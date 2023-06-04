import React, { useEffect } from "react";
import { useParams } from "react-router-dom";

import { Button, Grid, List, ListItem, Typography } from "@mui/material";
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";
import FavoriteIcon from "@mui/icons-material/Favorite";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import ScheduleIcon from "@mui/icons-material/Schedule";

import { getMovie, getMovieCast, getMovieComments } from "../../api/movie";
import Section from "../../components/Section";

import "./MovieDetails.css";
import { deepOrange, purple } from "@mui/material/colors";
import { useApplication } from "../../context";
import { convertDate, getHourAndMinute } from "../../utils/date";

const COMMENTS_PER_PAGE = 5;

const MovieDetails = () => {
  const path = useParams();
  const [movie, setMovie] = React.useState(null);
  const [cast, setCast] = React.useState([]);

  const [comment, setComment] = React.useState("");
  const [comments, setComments] = React.useState([]);
  const [commentPage, setCommentPage] = React.useState(1);

  useEffect(() => {
    const fetchMovie = async () => {
      const movie = await getMovie(path.id);
      const cast = await getMovieCast(path.id);

      setMovie(movie);
      setCast(cast);
    };

    fetchMovie();
  }, [path.id]);

  useEffect(() => {
    const fetchComments = async () => {
      const comments = await getMovieComments(path.id, {
        page: commentPage,
        size: COMMENTS_PER_PAGE,
      });
      setComments(comments);
    };

    fetchComments();
  }, [path.id, commentPage]);

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

          <Grid
            container
            sx={{
              width: "100%",
              height: "50px",
              fontSize: "14px",
              fontWeight: "700",
            }}
            pt={3}
          >
            <Grid item sx={{ width: "max-content" }} mr={2}>
              <span className="movie-details pegi">
                {movie?.language?.toUpperCase()}
              </span>
            </Grid>
            <Grid item sx={{ width: "max-content" }} mr={2}>
              <span className="movie-details quality">
                {movie?.rating * 10}%
              </span>
            </Grid>
            <Grid item sx={{ width: "max-content" }} mr={2}>
              <span className="movie-details">
                {movie?.genres.map((genre) => genre.name).join(", ")}
              </span>
            </Grid>
            <Grid item sx={{ width: "max-content" }} mr={1}>
              <span className="movie-details">
                <CalendarMonthIcon sx={{ fontSize: "18px" }} />
                {convertDate(movie?.releaseDate).getFullYear()}
              </span>
            </Grid>
            <Grid item sx={{ width: "max-content" }} mr={1}>
              <span className="movie-details">
                <ScheduleIcon sx={{ fontSize: "18px" }} />
                {getHourAndMinute(movie?.runtime)}
              </span>
            </Grid>
          </Grid>

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
          <Typography
            variant="h4"
            sx={{
              fontFamily: "'Poppins', sans-serif",
              fontWeight: 600,
              color: "#fff",
            }}
            mt={3}
          >
            Cast
          </Typography>
          <Grid item xs={12} pt={5} mt={1}>
            <Grid
              container
              spacing={5}
              pt={3}
              sx={{
                width: "100%",
                height: "300px",
                fontSize: "14px",
                fontWeight: "700",
              }}
            >
              <List
                sx={{
                  display: "flex",
                  flexDirection: "row",
                  gap: "20px",
                  overflowX: "scroll",
                  "&::-webkit-scrollbar": {
                    display: "none",
                  },
                }}
              >
                {cast && cast.length > 0 ? (
                  cast?.map((cast) => (
                    <ListItem
                      key={cast?.id}
                      sx={{
                        width: "max-content",
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                      }}
                    >
                      <img
                        src={
                          cast?.profilePath
                            ? `https://image.tmdb.org/t/p/original${cast.profilePath}`
                            : `../no-image.jpg`
                        }
                        alt={cast?.name}
                        width="100px"
                        height="100px"
                        style={{ borderRadius: "50%", objectFit: "cover" }}
                      />
                      <Typography
                        variant="body1"
                        sx={{
                          fontFamily: "'Poppins', sans-serif",
                          fontWeight: 600,
                          color: "#fff",
                          textAlign: "center",
                        }}
                      >
                        {cast?.name}
                      </Typography>
                      <Typography
                        variant="body2"
                        sx={{
                          fontFamily: "'Poppins', sans-serif",
                          fontWeight: 400,
                          color: "#fff",
                          textAlign: "center",
                        }}
                      >
                        {cast?.character}
                      </Typography>
                    </ListItem>
                  ))
                ) : (
                  <Typography
                    variant="body1"
                    sx={{
                      fontFamily: "'Poppins', sans-serif",
                      fontWeight: 600,
                      color: "#fff",
                      textAlign: "center",
                    }}
                  >
                    No Cast
                  </Typography>
                )}
              </List>
            </Grid>
          </Grid>

          <Typography
            variant="h4"
            sx={{
              fontFamily: "'Poppins', sans-serif",
              fontWeight: 600,
              color: "#fff",
            }}
            mt={3}
          >
            Comments
          </Typography>
          <Grid item xs={12} pt={5} mt={1}>
            <Grid
              container
              spacing={5}
              pt={3}
              sx={{
                width: "100%",
                height: "300px",
                fontSize: "14px",
                fontWeight: "700",
              }}
            >
              <List
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  gap: "5px",
                  overflowX: "scroll",
                  "&::-webkit-scrollbar": {
                    display: "none",
                  },
                }}
              >
                {comments && comments.length > 0 ? (
                  comments?.map((comment) => (
                    <ListItem
                      key={comment?.id}
                      sx={{
                        width: "max-content",
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                      }}
                    >
                      <Typography
                        variant="body1"
                        sx={{
                          fontFamily: "'Poppins', sans-serif",
                          fontWeight: 600,
                          color: "#fff",
                          textAlign: "center",
                        }}
                      >
                        {comment?.username}
                      </Typography>
                      <Typography>{comment?.comment}</Typography>
                    </ListItem>
                  ))
                ) : (
                  <Typography
                    variant="body1"
                    sx={{
                      fontFamily: "'Poppins', sans-serif",
                      fontWeight: 600,
                      color: "#fff",
                      textAlign: "center",
                    }}
                  >
                    No Comments
                  </Typography>
                )}
              </List>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </Section>
  );
};

export default MovieDetails;
