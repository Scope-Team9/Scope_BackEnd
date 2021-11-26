import React from "react";
import { Grid, Input, Button } from "../../elements/Index";
import styled from "styled-components";

const UserType = props => {
  const { myType } = props;
  console.log(myType);
  return (
    <>
      <Grid>
        {myType === "LVG" && <CardImg src="/img/호랑이.png"></CardImg>}
        {myType === "LVP" && <CardImg src="/img/늑대.png"></CardImg>}
        {myType === "LHG" && <CardImg src="/img/여우.png"></CardImg>}
        {myType === "LHP" && <CardImg src="/img/판다.png"></CardImg>}
        {myType === "FVG" && <CardImg src="/img/토끼.png"></CardImg>}
        {myType === "FVP" && <CardImg src="/img/허스키.png"></CardImg>}
        {myType === "FHG" && <CardImg src="/img/고양이.png"></CardImg>}
        {myType === "FHP" && <CardImg src="/img/물개.png"></CardImg>}
      </Grid>
    </>
  );
};

const CardImg = styled.img`
  width: 400px;
  height: 450px;
  object-fit: cover;
`;
export default UserType;
