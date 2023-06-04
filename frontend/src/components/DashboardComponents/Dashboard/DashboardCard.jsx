import { Card, Grid } from "@mui/material";
import React from "react";
import { convertToK } from "../../../utils/object";

const DashboardCard = ({ icon, title, value }) => {
  icon = React.cloneElement(icon, {
    sx: {
      fontSize: 48,
      color: "#fff",
      transform: "translate(0px, -40px)",
      position: "absolute",
      borderRadius: "50%",
    },
  });

  return (
    <Card
      sx={{
        padding: 2,
        backgroundColor: "#051d32",
        width: "300px",
        boxShadow: "0px 0px 10px 0px rgba(0,0,0,0.75)",
      }}
    >
      {icon}

      <Grid container spacing={2} mt={2} alignItems="center" height="100%" justifyContent={"center"} flexDirection={"column"} p={0} width={"100%"} margin={0}>
        <Grid item xs={12} md={12} p={0} sx={{ width: "100%", textAlign: "center" }}>
          <h4 style={{ color: "#fff", fontSize: "1.5rem", fontWeight: "400" }}>{title}</h4>
        </Grid>

        <Grid item xs={12} md={12} sx={{ padding: 0, width: "100%", textAlign: "center" }}>
          <h1 style={{ color: "#fff", fontSize: "3rem" }}>{convertToK(value)}</h1>
        </Grid>
      </Grid>
    </Card>
  );
};

export default DashboardCard;
