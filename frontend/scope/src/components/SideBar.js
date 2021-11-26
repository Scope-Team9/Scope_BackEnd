/* eslint-disable */
import React from "react";
import { Grid, Image, Text } from "../elements/Index";
import userImage from "../images/임시로고.jpg";

const SideBar = () => {
  return (
    <React.Fragment>
      <Grid
        position="fixed"
        top="30%"
        left="2%"
        width="70px"
        height="200px"
        padding="16px 0 20% 0"
        borderRadius="300px"
        textAlign="center"
        display="flex"
        flexDirection="column"
        justifyContent="space-between"
        bg="#8B3FF8"
        zIndex="0"
        marginRight="100px"
      >
        <Grid margin="20px auto" padding="0 10px">
          <Grid
            width="90%"
            height="46px"
            display="flex"
            alignItems="center"
            padding="0 8px"
            hover="rgba(0, 0, 0, 0.05)"
            borderRadius="10px"
          >
            <Image src={userImage} size="48" />
          </Grid>
        </Grid>
        <Grid margin="20px auto" padding="0 10px">
          <Grid
            width="90%"
            height="46px"
            display="flex"
            alignItems="center"
            padding="0 8px"
            hover="rgba(0, 0, 0, 0.05)"
            borderRadius="10px"
          >
            <Image src={userImage} size="48" />
          </Grid>
        </Grid>
        <Grid margin="20px auto" padding="0 10px">
          <Grid
            width="100%"
            height="46px"
            display="flex"
            alignItems="center"
            padding="0 8px"
            hover="rgba(0, 0, 0, 0.05)"
            borderRadius="10px"
          >
            <Image src={userImage} size="48" />
          </Grid>
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

export default SideBar;
