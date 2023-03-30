import React, { memo } from "react";
import { useLocation } from "react-router-dom";

import { Box, Container } from "@mui/system";
import { Button, Divider, Grid, Link, Typography } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

import "./Header.css";
import { useApplication } from "../../context";
import UserHeader from "../UserHeader";

const Header = () => {
  const [search, setSearch] = React.useState("");

  const context = useApplication();
  const location = useLocation();

  const handleSubmit = (e) => {
    e.preventDefault();

    window.location.href = `/movies${search ? `?search=${search}` : ""}`;
  };
  return (
    <header>
      <Box
        className={
          "sticky-header" + (context.isNavTransparent ? " transparent" : "")
        }
      >
        <Container maxWidth="m" sx={{ py: 2 }}>
          <Grid container spacing={4}>
            <Grid item xs={12} sm={6} md={2}>
              <div className="logo">
                <img src="/logo.png" alt="Logo" />
                <Typography
                  variant="h6"
                  component="h1"
                  sx={{
                    color: "#fff",
                    fontWeight: "800",
                    fontFamily: "Popins, sans-serif",
                  }}
                >
                  <Link href="/" underline="none" color="inherit">
                    SQLCinema
                  </Link>
                </Typography>
              </div>
            </Grid>
            <Grid item xs={12} sm={6} md={context.isAuth ? 8 : 9}>
              <div className="menu">
                <ul>
                  <li>
                    <Link className="nav-link" href="/">
                      Home
                    </Link>
                  </li>
                  <li>
                    <Link className="nav-link" href="/movies">
                      Movies
                    </Link>
                  </li>
                  <li>
                    <div className="search-box">
                      <form onSubmit={handleSubmit}>
                        <input
                          type="text"
                          placeholder="Search"
                          value={search}
                          onChange={(e) => setSearch(e.target.value)}
                        />
                        <Button
                          type="submit"
                          onClick={handleSubmit}
                          sx={{
                            color: "#171d22",
                            background: "transparent",
                            padding: "0",
                          }}
                        >
                          <SearchIcon
                            sx={{
                              background: "#f0f0f0",
                              height: "41px",
                              width: "100%",
                              borderRadius: "0 50px 50px 0",
                            }}
                          />
                        </Button>
                      </form>
                    </div>
                  </li>
                  <Divider
                    orientation="vertical"
                    flexItem
                    sx={{ borderColor: "#fafafa !important" }}
                  />
                </ul>
              </div>
            </Grid>
            <Grid
              item
              xs={12}
              sm={6}
              md={context.isAuth ? 2 : 1}
              justifyContent="center"
              display="flex"
            >
              {context.isAuth ? (
                <UserHeader />
              ) : (
                <div className="login-btn">
                  <Link
                    href={
                      location.pathname === "/login" ? "/register" : "/login"
                    }
                  >
                    {location.pathname === "/login" ? "Register" : "Login"}
                  </Link>
                </div>
              )}
            </Grid>
          </Grid>
        </Container>
      </Box>
    </header>
  );
};

export default memo(Header);
