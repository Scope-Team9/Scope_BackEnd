import React from "react";
import { Grid } from "../../elements/Index";
import LeftBanner from "./LeftBanner";
const Message = (props) => {
  return (
    <React.Fragment>
      <Grid
        display="flex"
        justifyContent="center"
        maxWidth="1920px"
        height="100%"
        border="1px solid #C4C4C4"
        margin="auto"
      >
        <LeftBanner />
      </Grid>
    </React.Fragment>
  );
};

export default Message;
