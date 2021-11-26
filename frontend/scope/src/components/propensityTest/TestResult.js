import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Grid, Button, Image, Text } from "../../elements/Index";
import UserType from "./UserType";

const TestResult = props => {
  const userType = useSelector(state => state.user.userPropensityType);
  console.log(props.userType);
  console.log(userType);

  return (
    <Grid textAlign="center" width="90%" margin="auto">
      <Grid textAlign="left">
        <Text bold size="12px">
          당신의 성향은?
        </Text>
      </Grid>
      <Grid display="flex" justifyContent="center">
        {userType === "LVG" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                LVG - 리더 / 수직 / 결과 - 호랑이
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                호랑이는 팀을 이끄는 리더형입니다! <br />
                상하관계가 없는 수평적인 조직보다
                <br /> 조직적으로 움직이는 수직적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 과정보다 결과를 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
        {userType === "LVP" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                LVP - 리더 / 수직 / 과정 - 늑대
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                늑대는 팀을 이끄는 리더형입니다!
                <br />
                상하관계가 없는 수평적인 조직보다
                <br /> 조직적으로 움직이는 수직적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 결과보다 과정을 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
        {userType === "LHG" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                LHG - 리더 / 수평 / 결과 - 여우
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                여우는 팀을 이끄는 리더형입니다!
                <br />
                상하관계가 있는 수직적인 조직보다
                <br /> 자유로운 수평적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 과정보다 결과를 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
        {userType === "LHP" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                LHP - 리더 / 수평 / 과정 - 판다
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                판다는 팀을 이끄는 리더형입니다! <br />
                상하관계가 있는 수직적인 조직보다
                <br /> 자유로운 수평적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 결과보다 과정을 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
        {userType === "FVG" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                FVG - 팔로잉 / 수직 / 결과 - 토끼
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                토끼는 팀의 원동력인 팔로잉형입니다! <br />
                상하관계가 없는 수평적인 조직보다
                <br /> 조직적으로 움직이는 수직적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 과정보다 결과를 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
        {userType === "FVP" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                FVP - 팔로잉 / 수직 / 과정 - 시바
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                시바는 팀의 원동력인 팔로잉형입니다! <br />
                상하관계가 없는 수평적인 조직보다
                <br /> 조직적으로 움직이는 수직적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 결과보다 과정을 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
        {userType === "FHG" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                FHG - 팔로잉 / 수평 / 결과 - 고양이
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                고양이는 팀의 원동력인 팔로잉형입니다! <br />
                상하관계가 있는 수직적인 조직보다
                <br /> 자유로운 수평적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 과정보다 결과를 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
        {userType === "FHP" && (
          <Grid>
            <Grid
              bg="#554475"
              borderRadius="20px"
              padding="12px 0"
              margin="12px 0"
            >
              <Text size="14px" color="#fff">
                FHP - 팔로잉 / 수평 / 과정 - 물개
              </Text>
            </Grid>
            <Grid
              margin="12px 0"
              borderRadius="20px"
              border="1px solid #554475"
              padding="40px 0"
            >
              <Text size="12px">
                물개는 팀의 원동력인 팔로잉형입니다! <br />
                상하관계가 있는 수직적인 조직보다
                <br /> 자유로운 수평적인 조직에 잘 어울려요!
                <br /> 일을 수행함에 있어 결과보다 과정을 중요하게 생각하시네요!
              </Text>
            </Grid>
          </Grid>
        )}
      </Grid>
    </Grid>
  );
};

export default TestResult;
