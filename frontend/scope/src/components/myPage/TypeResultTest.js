/* eslint-disable */

import React from "react";
import { Dialog } from "@material-ui/core";
import { Grid, Image, Text, Button } from "../../elements/Index";
import PropensityTest from "../propensityTest/PropensityTest";
import styled from "styled-components";
import { map, stubFalse } from "lodash";
import { Grid4x4 } from "@mui/icons-material";
import EmailAuth from "../EmailAuth";

const TypeResultTest = (props) => {
  const [myData, setMyData] = React.useState();
  const [arr, setArr] = React.useState([
    {
      id: "LVG",
      type: "리더",
      type2: "수직",
      type3: "결과",
      text1: `리더형인 당신은 `,
      text2: "리더 이지만 수직적 리더십",
      text3: "을 원해요!",
      text4:
        "과정보다는 결과를 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "LVP",
      type: "리더",
      type2: "수직",
      type3: "과정",
      text1: `리더형인 당신은 `,
      text2: "리더 이지만 수직적 리더십",
      text3: "을 원해요!",
      text4:
        " 결과보다는 과정을 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "LHG",
      type: "리더",
      type2: "수평",
      type3: "결과",
      text1: `리더형인 당신은 `,
      text2: "리더 이지만 수평적 리더십",
      text3: "을 원해요!",
      text4:
        "과정보다는 결과를 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "LHP",
      type: "리더",
      type2: "수평",
      type3: "과정",
      text1: `리더형인 당신은 `,
      text2: "리더 이지만 수평적 리더십",
      text3: "을 원해요!",
      text4:
        "결과보다는 과정을 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "FVG",
      type: "팔로워",
      type2: "수직",
      type3: "결과",
      text1: `팔로우형 당신은 `,
      text2: "팔로워 이지만 수직적 팔로워십",
      text3: "을 원해요!",
      text4:
        "과정보다는 결과를 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "FVP",
      type: "팔로워",
      type2: "수직",
      type3: "과정",
      text1: `팔로우형 당신은 `,
      text2: "팔로워 이지만 수직적 팔로워십",
      text3: "을 원해요!",
      text4:
        "결과보다는 과정을 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "FHG",
      type: "팔로워",
      type2: "수평",
      type3: "결과",
      text1: `팔로우형 당신은 `,
      text2: "팔로워 이지만 수평적 팔로워십",
      text3: "을 원해요!",
      text4:
        "과정보다는 결과를 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "FHP",
      type: "팔로워",
      type2: "수평",
      type3: "과정",
      text1: `팔로우형 당신은 `,
      text2: "팔로워 이지만 수평적 팔로워십",
      text3: "을 원해요!",
      text4:
        "결과보다는 과정을 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
    {
      id: "RHP",
      type: "팔로워",
      type2: "수평",
      type3: "과정",
      text1: `팔로우형 당신은 `,
      text2: "팔로워 이지만 수평적 팔로워십",
      text3: "을 원해요!",
      text4:
        "결과보다는 과정을 중요시하는 당신은 우리 스코프 사이드 프로젝트에 적합한 사람!",
    },
  ]);

  React.useEffect(() => {
    console.log("테스트결과", props);
    arr.map((item) => {
      if (item.id === props.myType) {
        setMyData(item);
      }
    });
  }, []);

  return (
    <Grid>
      {myData && (
        <Grid
          position="relative"
          margin="-1020px 0 0 33%"
          width="50.3%"
          zIndex="1"
        >
          <Grid
            margin="-0px 0 0 0"
            display="flex"
            width="100%"
            justifyContent="space-between"
            // border="1px solid #333"
            borderRadius="15px"
          >
            <MyResultDiv>
              <MyResultText>{myData.type}</MyResultText>
              <MyResultText>{myData.type2}</MyResultText>
              <MyResultText>{myData.type3}</MyResultText>
            </MyResultDiv>
          </Grid>
          <Dialog maxWidth={"sm"} scroll="paper" open={props.testmodal}>
            <Grid width="550px" height="100%">
              <PropensityTest />
            </Grid>
          </Dialog>
          <Grid margin="0 0 0 15px" width="600px">
            <Grid display="flex" width="500px%">
              <MyResultText2>{myData.text1}</MyResultText2>
              <MyResultTextBold>{myData.text2}</MyResultTextBold>
              <MyResultText2>{myData.text3}</MyResultText2>
            </Grid>
            <Grid display="flex" width="600px">
              <MyResultText2>{myData.text4}</MyResultText2>
            </Grid>
            {props.userId == props.myUserId &&
              props.mydata?.isMyMypage === true && (
                <Grid
                  width="140%"
                  display="flex"
                  justifyContent="space-between"
                  alignItems="center"
                >
                  <GotoTest
                    onClick={() => {
                      props.EditTest();
                    }}
                  >
                    성향 테스트 다시하기⇀
                  </GotoTest>
                  <ConfirmEmail
                    onClick={() => {
                      props.onClick();
                    }}
                  >
                    이메일 인증하기
                  </ConfirmEmail>
                  <EmailAuth modal={props.modal} setModal={props.setModal} />
                </Grid>
              )}
            <Dialog maxWidth={"sm"} scroll="paper" open={props.testmodal}>
              <Grid width="550px" height="100%">
                <PropensityTest TestClose={props.TestClose} />
              </Grid>
            </Dialog>
          </Grid>
        </Grid>
      )}
    </Grid>
  );
};

const MyResultDiv = styled.div`
  display: flex;
  width: auto;
  align-items: center;

  @media screen and (max-width: 1400px) {
    margin-top: 1100px;
  }
  @media screen and (max-width: 750px) {
    margin-top: 1100px;
  } ;
`;

const MyResultText = styled.div`
  width: 70px;
  height: 40px;
  border-radius: 12px;
  background-color: #b29cf4;
  color: white;
  align-items: center;
  display: flex;
  justify-content: center;
  margin-left: 10px;
  font-size: 20px;
  font-weight: bold;
`;

const GotoTest = styled.p`
  font-size: 15px;
  font-weight: bold;
  cursor: pointer;
  /* float: right; */
  margin-right: 10px;
  margin-top: 30px;
`;
const MyResultText2 = styled.p`
  color: white;
  font-size: 17px;
  height: 17px;
  width: auto;
`;
const MyResultTextBold = styled.p`
  color: white;
  font-size: 17px;
  height: 17px;
  font-weight: bold;
`;

const ConfirmEmail = styled.button`
  width: 160px;
  height: 35px;
  padding: 8px 20px;
  border: 1px solid white;
  background-color: transparent;
  color: white;
  border-radius: 10px;
  z-index: 99999;
  cursor: pointer;

  &:hover {
    color: black;
    background-color: white;
    opacity: 0.7;
  }

  @media screen and (max-width: 750px) {
    color: black;
  } ;
`;
export default TypeResultTest;
