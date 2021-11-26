/* eslint-disable */
import React from "react";
import styled from "styled-components";
import { Grid, Image } from "../elements/Index";
import Logo from "../images/Logo.png";
import { history } from "../redux/configureStore";

const HeaderLeft = () => {
  return (
    <Grid>
      <LogoDiv>
        <img
          style={{ cursor: "pointer" }}
          onClick={() => {
            history.push("/");
          }}
          src={Logo}
          width="60%"
        />
      </LogoDiv>
    </Grid>
  );
};
const LogoDiv = styled.div`
  width: 15%;
  @media screen and (max-width: 750px) {
    width: 35%;
  } ;
`;
export default HeaderLeft;
