import React, { useLayoutEffect } from "react";
import Section from "../../components/Section";
import { Card, Grid, Paper, Typography } from "@mui/material";
import { getUser } from "../../api/user";

const Profile = () => {
  const [user, setUser] = React.useState();

  useLayoutEffect(() => {
    const fetchUser = async () => {
      const response = await getUser();
      console.log(
        "🚀 ~ file: Profile.jsx:12 ~ fetchUser ~ response:",
        response
      );

      setUser(response.data);
    };
    fetchUser();
  }, []);

  return (
    <>
      <Section bgImage="bg.jpg" height="100vh" opacity="0.5">
        <Paper elevation={3} width="100%" height="100%">
          <p>dxas</p>
        </Paper>
      </Section>
    </>
  );
};

export default Profile;
