import {
  Avatar,
  Grid,
  IconButton,
  Menu,
  MenuItem,
  Tooltip,
} from "@mui/material";
import React from "react";

import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import ShopIcon from "@mui/icons-material/Shop";
import StarIcon from "@mui/icons-material/Star";
import LogoutIcon from "@mui/icons-material/Logout";

const styles = {
  iconButton: {
    color: "#fafafa",
    transition: "all 0.4s ease",
    ["&:hover"]: { color: "#119eba" },
  },

  menuItem: {
    color: "#fafafa",
    fontFamily: "Poppins, sans-serif",
    fontWeight: "600",
    transition: "all 0.4s ease",
    textAlign: "center",
    display: "flex",
    justifyContent: "space-between",
    ["&:hover"]: { color: "#119eba" },
  },
};

const UserHeader = () => {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <>
      <Grid container spacing={5}>
        <Grid item xs={12} md={3}>
          <Tooltip title="Your Cart" placement="bottom" arrow>
            <IconButton sx={styles.iconButton}>
              <ShoppingCartIcon
                sx={{
                  fontSize: "xx-large",
                }}
              />
            </IconButton>
          </Tooltip>
        </Grid>
        <Grid item xs={12} md={3}>
          <Tooltip title="Your Purchased Tickets" placement="bottom" arrow>
            <IconButton sx={styles.iconButton}>
              <ShopIcon
                sx={{
                  fontSize: "xx-large",
                }}
              />
            </IconButton>
          </Tooltip>
        </Grid>
        <Grid
          item
          xs={12}
          md={5}
          flexGrow={1}
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <IconButton
            id="profile"
            onClick={handleClick}
            sx={{
              width: "32px",
              height: "32px",
            }}
          >
            <Avatar alt="User" src="/avatar.jpg" p={1}></Avatar>
          </IconButton>
          <Menu
            anchorEl={anchorEl}
            id="account-menu"
            open={open}
            onClose={handleClose}
            onClick={handleClose}
            PaperProps={{
              elevation: 0,
              sx: {
                width: "max-content",
                overflow: "visible",
                backgroundColor: "rgba(44, 48, 53, 0.8)",
              },
            }}
            transformOrigin={{ horizontal: "right", vertical: "top" }}
            anchorOrigin={{ horizontal: "right", vertical: "bottom" }}
          >
            <MenuItem sx={styles.menuItem} onClick={handleClose}>
              My account
            </MenuItem>
            <MenuItem sx={styles.menuItem} onClick={handleClose}>
              Favorites
              <StarIcon sx={{ ml: 1 }} />
            </MenuItem>
            <MenuItem sx={styles.menuItem} onClick={handleClose}>
              Logout
              <LogoutIcon sx={{ ml: 1 }} />
            </MenuItem>
          </Menu>
        </Grid>
      </Grid>
    </>
  );
};

export default UserHeader;
