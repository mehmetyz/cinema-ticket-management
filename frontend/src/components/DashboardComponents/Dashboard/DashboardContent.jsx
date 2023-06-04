import { Chip, Grid, List, TablePagination, Typography } from "@mui/material";
import React, { useEffect, useState } from "react";
import { loadUser } from "../../../utils/localStorage";
import DashboardCard from "./DashboardCard";

import PersonIcon from "@mui/icons-material/Person";
import MovieIcon from "@mui/icons-material/Movie";
import AttachMoneyIcon from "@mui/icons-material/AttachMoney";
import ConfirmationNumberIcon from "@mui/icons-material/ConfirmationNumber";

import { getMovieCount } from "../../../api/movie";
import { getUsers } from "../../../api/user";
import { convertToK } from "../../../utils/object";
import RecentActivities from "../RecentActivities";

const colors = {
  ADMIN: "#4C4C6D",
  USER_MANAGER: "#1B9C85",
  THEATRE_MANAGER: "#F266AB",
  USER: "#00818c",
};

const DashboardContent = () => {
  const [movieCount, setMovieCount] = useState(0);
  const [userCount, setUserCount] = useState(0);
  const [ticketCount, setTicketCount] = useState(0);
  const [revenue, setRevenue] = useState(5000);

  useEffect(() => {
    const fetchMovieCount = async () => {
      const res = await getMovieCount(0);
      setMovieCount(res);
    };
    fetchMovieCount();
  }, []);

  useEffect(() => {
    const fetchUserCount = async () => {
      const res = await getUsers();
      setUserCount(res.data.length);
    };
    fetchUserCount();
  }, []);

  return (
    <Grid>
      <Typography
        variant="h4"
        component="h2"
        gutterBottom
        sx={{ color: "#fff" }}
      >
        <b>Welcome</b>, {loadUser().username}{" "}
        <Chip
          label={loadUser().role.replace("_", " ")}
          sx={{
            backgroundColor: colors[loadUser().role],
            color: "#fff",
            borderRadius: 0,
            marginLeft: 2,
          }}
        />
      </Typography>

      <Grid container spacing={2} mt={2}>
        <Grid item xs={12} md={3}>
          <DashboardCard
            title="Revenue"
            value={convertToK(revenue) + " $"}
            icon={<AttachMoneyIcon />}
          />
        </Grid>
        <Grid item xs={12} md={3}>
          <DashboardCard
            title="Users"
            value={convertToK(userCount)}
            icon={<PersonIcon />}
          />
        </Grid>
        <Grid item xs={12} md={3}>
          <DashboardCard
            title="Movies"
            value={convertToK(movieCount)}
            icon={<MovieIcon />}
          />
        </Grid>

        <Grid item xs={12} md={3}>
          <DashboardCard
            title="Sold Tickets"
            value={convertToK(ticketCount)}
            icon={<ConfirmationNumberIcon />}
          />
        </Grid>
      </Grid>

      <Grid container mt={2}>
        <Typography
          variant="h4"
          component="h2"
          gutterBottom
          sx={{ color: "#fff" }}
        >
          <b>Recent Activities</b>
        </Typography>
        <RecentActivities />
      </Grid>
    </Grid>
  );
};

export default DashboardContent;
