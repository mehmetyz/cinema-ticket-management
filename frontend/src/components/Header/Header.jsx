import React from "react";

import { Box, Container } from "@mui/system";
import { Button, Divider, Grid, Link, Typography } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

import "./Header.css";

const Header = () => {
  const [isTransparent, setIsTransparent] = React.useState(true);
  const [search, setSearch] = React.useState("");

  React.useEffect(() => {
    const handleScroll = () => {
      const currentScrollY = window.scrollY;
      if (currentScrollY > 0) {
        setIsTransparent(false);
      } else {
        setIsTransparent(true);
      }
    };

    window.addEventListener("scroll", handleScroll, { passive: true });

    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!search) return;

    window.location.href = `/all-movies?search=${search}`;
  };
  return (
    <header>
      <Box className={"sticky-header" + (isTransparent ? " transparent" : "")}>
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
                  SQLFlix
                </Typography>
              </div>
            </Grid>
            <Grid item xs={12} sm={6} md={9}>
              <div className="menu">
                <ul>
                  <li>
                    <Link className="nav-link" href="/">
                      Home
                    </Link>
                  </li>
                  <li>
                    <Link className="nav-link" href="/new-movies">
                      New Movies
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
              md={1}
              sx={{ justifyContent: "flex-end" }}
            >
              <div className="login-btn">
                <a href="/login">Login</a>
              </div>
            </Grid>
          </Grid>
        </Container>
      </Box>
    </header>
  );
};

export default Header;
