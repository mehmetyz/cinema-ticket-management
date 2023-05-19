import React, { memo, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import { produce } from "immer";

import { Alert, Snackbar } from "@mui/material";

import NoPage from "./pages/NoPage";
import Authentication from "./pages/Authentication";
import Home from "./pages/Home";
import Movies from "./pages/Movies";
import Profile from "./pages/Profile";
import MovieDetails from "./pages/MovieDetails";

import Header from "./components/Header";

import ApplicationContext from "./context";

import { getGenres } from "./api/movie";
import { getUser, login, logout, register } from "./api/user";

import { handleScroll } from "./utils/resize";
import { addItem, loadAuthToken, loadUser } from "./utils/localStorage";
import { navigate } from "./utils/navigate";

import "./App.css";
import Checkout from "./pages/Checkout";

function App() {
  const [genres, setGenres] = React.useState([]);
  const [context, setContext] = React.useState({
    isAuth: loadAuthToken() && loadUser()  ? true : false,
    isNavTransparent: true,
    snackBarMessage: "",
    snackBarOpen: false,

    showSnackBar: (message, messageType) => {
      setContext(
        produce((draft) => {
          draft.snackBarMessage = message;
          draft.snackBarMessageType = messageType;
          draft.snackBarOpen = true;
        })
      );
    },

    closeSnackBar: () => {
      setContext(
        produce((draft) => {
          draft.snackBarMessage = "";
          draft.snackBarOpen = false;
        })
      );
    },

    login: async (username, password) => {
      const response = await login(username, password);
      if (response.status === 200 && response.data) {
        const { token } = response.data;
        addItem("auth_token", token);

        const userData = await getUser();
        addItem("user", userData.data);

        context.showSnackBar("Login successful", "success");
        setTimeout(() => {
          setContext(
            produce((draft) => {
              draft.isAuth = true;
            })
          );
        }, 1000);
        navigate("/", 1000);
      }
    },
    logout: async () => {
      await logout();
      setContext(
        produce((draft) => {
          draft.isAuth = false;
        })
      );
      context.showSnackBar("Logout successful", "success");
    },

    register: async (username, email, password) => {
      const response = await register(username, email, password);
      if (response.status === 200 && response.data) {
        context.showSnackBar("Register successful", "success");
        navigate("/login", 1000);
      }
    },

    enableNavTransparent: (flag) => {
      setContext(
        produce((draft) => {
          draft.isNavTransparent = flag;
        })
      );
    },
  });

  useEffect(() => {
    const fetchGenres = async () => {
      const genres = await getGenres();
      genres.push({ genreId: 0, name: "All" });
      setGenres(
        genres.sort((a, b) => {
          if (a.genreId == 0) return -1;
          return a.name.localeCompare(b.name);
        })
      );
    };
    fetchGenres();
  }, []);

  useEffect(() => {
    window.addEventListener("scroll", () =>
      handleScroll(window.scrollY > 0, context)
    );
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <>
      <ApplicationContext.Provider value={context}>
        <Header movieGenres={genres}></Header>
        <Routes>
          <Route path="/" element={<Home genres={genres} />} exact />
          <Route
            path="/login"
            element={
              context.isAuth ? (
                <Home genres={genres} />
              ) : (
                <Authentication login />
              )
            }
          />
          <Route
            path="/register"
            element={
              context.isAuth ? <Home genres={genres} /> : <Authentication />
            }
          />

          

          {context.isAuth && <Route path="/profile" element={<Profile />} />}
          <Route path="/movies" element={<Movies genres={genres} />} />
          <Route path="/movies/:id" element={<MovieDetails />} />
          <Route
            path="/movies/:id/checkout"
            element={
              context.isAuth ? (
                <Checkout />
              ) : (
                <Authentication login />
              )
            }
          />
          <Route path="*" element={<NoPage />} />
        </Routes>
        <Snackbar
          open={context.snackBarOpen}
          autoHideDuration={6000}
          onClose={context.closeSnackBar}
          sx={{
            width: "20%",
          }}
        >
          <Alert
            onClose={context.closeSnackBar}
            severity={context.snackBarMessageType}
            sx={{ width: "100%" }}
          >
            {context.snackBarMessage}
          </Alert>
        </Snackbar>
      </ApplicationContext.Provider>
    </>
  );
}
export default memo(App);
