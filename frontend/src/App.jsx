import React from "react";
import Header from "./components/Header";
import Home from "./pages/Home";

import { Routes, Route } from "react-router-dom";

import "./App.css";
import genre from "./api/genre";
import NoPage from "./pages/NoPage";
import Authentication from "./pages/Authentication";

function App() {
  const genres = genre();

  return (
    <>
      <Header movieGenres={genres}></Header>
      <Routes>
        <Route path="/" element={<Home genres={genres} />} exact />
        <Route path="/login" element={<Authentication login />} />
        <Route path="/register" element={<Authentication />} />
        <Route path="*" element={<NoPage />} />
      </Routes>
    </>
  );
}
export default App;
