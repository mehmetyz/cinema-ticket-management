import React from "react";

import { Button, Grid, Typography, Avatar } from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { blue } from "@mui/material/colors";

const styles = {
  primary: {
    color: blue[500],
    dark: "#2f3439",
  },
};
const Register = () => {
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
          padding: "80px 0",
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
          <Typography
            component="h1"
            variant="h5"
            sx={{
              fontFamily: "inherit",
              fontWeight: "700",
              letterSpacing: "0.1em",
              textTransform: "uppercase",
              fontSize: "2rem",
            }}
          >
            Register
          </Typography>
        </div>
        <div className="login-form-container">
          <form onSubmit={handleSubmit} noValidate>
            <input type="text" placeholder="Username" autoFocus />
            <input type="text" placeholder="Email" />
            <input type="password" placeholder="Password" />
            <div>
              <Button
                type="submit"
                variant="contained"
                color="primary"
                sx={{ width: "100%", fontWeight: "600" }}
              >
                Register
              </Button>
              <Button
                type="submit"
                variant="outlined"
                sx={{
                  width: "100%",
                  fontWeight: "600",
                  color: styles.primary.color,
                  border: "1px solid transparent",
                }}
                href="/login"
              >
                Have an account?
              </Button>
            </div>
          </form>
        </div>
      </Grid>
    </>
  );
};

export default Register;
