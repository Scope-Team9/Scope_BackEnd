/* eslint-disable */
import { Grid, Image, Text, Button } from "../elements/Index";
import styled from "styled-components";
import React from "react";

const Spinner = () => {
  return (
    <React.Fragment>
      <Grid width="30%" margin="auto">
        <IMG src="/img/spinner.gif" alt="spinner"></IMG>
      </Grid>
    </React.Fragment>
  );
};

const IMG = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
  margin: auto;
`;

export default Spinner;
