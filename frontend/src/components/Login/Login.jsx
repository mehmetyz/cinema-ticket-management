import React from "react";

import { Avatar, Grid, Button, Typography } from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { blue } from "@mui/material/colors";

import "./Login.css";

const styles = {
  primary: {
    color: blue[500],
    dark: "#171d22",
  },
};
const Login = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
  };
  return (
    <>
      <Grid
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          width: "100%",
          maxWidth: "30%",
          padding: 10,
          justifyContent: "flex-start",
          boxShadow: "0 0 10px 0 rgba(0, 0, 0, 0.8)",
          borderRadius: 5,
          backgroundColor: styles.primary.dark,
          gap: 3,
        }}
      >
        <div className="login-form-header">
          <Avatar sx={{ m: 1, bgcolor: styles.primary.color }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Login
          </Typography>
        </div>

        <form onSubmit={handleSubmit} noValidate style={{ height: "100%" }}>
          <div className="login-form-container">
            <input type="text" placeholder="Username" />
            <input type="password" placeholder="Password" />
            <row>
              <Button
                type="submit"
                variant="contained"
                color="primary"
                sx={{ width: "100%", fontWeight: "600" }}
              >
                Login
              </Button>
              <Button
                type="submit"
                variant="outlined"
                sx={{
                  width: "100%",
                  fontWeight: "600",
                  color: styles.primary.color,
                }}
                href="/register"
              >
                Register
              </Button>
            </row>
          </div>
        </form>
      </Grid>
    </>
  );
};

export default Login;
