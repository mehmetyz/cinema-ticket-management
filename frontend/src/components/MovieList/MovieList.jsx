import React from "react";
import { Grid, Link, Pagination, PaginationItem } from "@mui/material";

import MovieCard from "../MovieCard";

import { useApplication } from "../../context";

import { calcPage } from "../../utils/resize";
import { handleScroll } from "../../utils/resize";
import { blue } from "@mui/material/colors";

const MovieList = ({ movies, genre, pagination, props }) => {
  const context = useApplication();

  return (
    <Grid
      container
      maxWidth="m"
      {...props}
      sx={{
        alignItems: "start",
        justifyContent: "center",
        width: "100%",
        heigth: "auto",
        maxHeight: "90%",
        display: "flex",
        flexWrap: "wrap",

        rowGap: "30px",
        columnGap: "15px",
        overflow: "scroll",

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
      onScroll={(e) => handleScroll(e.target.scrollTop > 0, context)}
    >
      {movies &&
        movies
          .slice(...calcPage(pagination.page ?? 1))
          .map((movie) => <MovieCard movie={movie} key={movie.id} />)}

      {pagination && pagination.page && pagination.count ? (
        <Pagination
          color="primary"
          count={Math.ceil(movies.length / pagination.count)}
          page={pagination.page}
          onChange={(_, page) => pagination.setPage(page)}
          renderItem={(item) => (
            <PaginationItem
              {...item}
              sx={{
                backgroundColor: "#fff",
                border: "none",
                ["&:hover"]: {
                  backgroundColor: "#fafafa",
                },
              }}
            />
          )}
          sx={{
            bottom: 0,
            position: "absolute",
            width: "90%",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            mt: 2,
          }}
        />
      ) : (
        <Link
          href={`/movies?genre=${genre ? genre : "0"}`}
          underline="none"
          sx={{
            color: "#fff",
            fontSize: "15px",
            backgroundColor: blue[700],
            padding: "8px",
            borderRadius: "5px",
            transition: "all 0.3s ease-in-out",
            ["&:hover"]: {
              backgroundColor: blue[500],
            },

            display: "flex",
            alignItems: "center",
          }}
        >
          <b>{">>>"}</b> &nbsp; Load More
        </Link>
      )}
    </Grid>
  );
};

export default MovieList;
