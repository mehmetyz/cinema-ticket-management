import React from "react";
import Section from "../../components/Section";
import { Grid, Typography } from "@mui/material";
import { loadUser } from "../../utils/localStorage";

const Profile = () => {
  const [user, setUser] = React.useState(loadUser());
  return (
    <>
      <Section bgImage="bg.jpg" height="100vh" opacity="0.5">
        <Grid
          container
          justifyContent="flex-start"
          alignItems="center"
          flexDirection={"column"}
          height={"70%"}
        >
          <Grid item xs={12} sm={6} md={4}>
            <Typography variant="h4" color="white" textAlign="center" fontWeight={600}>
              Profile
            </Typography>
          </Grid>
        </Grid>
      </Section>
    </>
  );
};

export default Profile;
