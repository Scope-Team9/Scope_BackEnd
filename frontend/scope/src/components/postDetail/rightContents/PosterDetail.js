import React from "react";
import { Grid, Text } from "../../../elements/Index";
import styled from "styled-components";
import UserList from "../../UserList";

const PosterDetail = (props) => {
  return (
    <React.Fragment>
      <Grid>
        <Text size="18px" bold>
          게시자
        </Text>
        <UserList list={props.passedData?.propensityType}></UserList>
        <Name>{props.passedData?.nickname}</Name>
        <Mbti>({props.passedData?.propensityType})</Mbti>
      </Grid>
    </React.Fragment>
  );
};

const Container = styled.div`
  text-align: center;
`;

const Name = styled.div`
  font-size: 16px;
`;

const Mbti = styled.div`
  font-size: 16px;
`;

export default PosterDetail;
