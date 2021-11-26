/* eslint-disable */
import React from "react";
import { useSelector } from "react-redux";
import { Image, Grid, Button, Text } from "../../elements/Index";
import styled from "styled-components";
import Symbol from "../../images/111111.png";
import PropensityTest from "../propensityTest/PropensityTest";
import { Dialog } from "@material-ui/core";

const TopBanner = () => {
  const isToken = document.cookie.split("=")[1];
  const [showModal, setShowModal] = React.useState(false);

  return (
    <>
      <Grid width="70vw" margin="0 auto 7rem auto">
        <Grid
          display="flex"
          width="100%"
          justifyContents="space-between"
          alignItems="end"
        >
          {/* <Grid>
            <Button
              width="150px"
              _onClick={() => {
                isToken
                  ? setShowModal(true)
                  : window.alert("로그인이 필요합니다");
              }}
            >
              성향테스트
            </Button>
            <Dialog maxWidth={"sm"} scroll="paper" open={showModal}>
              <Grid width="550px" height="100%">
                <PropensityTest />
              </Grid>
            </Dialog>
          </Grid> */}

          <img style={{ width: "100%", height: "50%" }} src={Symbol} />
        </Grid>
      </Grid>
    </>
  );
};

const Title = styled.h1`
  font-size: 3rem;
  margin: 1.3rem 0;
  letter-spacing: 0.05rem;
`;

const Sub = styled.span`
  font-size: 1.5rem;
  letter-spacing: 0.01rem;
`;

export default TopBanner;
