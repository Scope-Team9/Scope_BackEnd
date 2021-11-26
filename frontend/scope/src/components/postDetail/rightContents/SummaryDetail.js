import React from "react";
import styled from "styled-components";
import { Grid, Text } from "../../../elements/Index";

const SummaryDetail = (props) => {
  return (
    <React.Fragment>
      <Grid margin="10px auto">
        <Text color="#C4C4C4" size="20px" bold>
          <Text>소개 : </Text> {props.passedData?.summary}
          <Line />
        </Text>
      </Grid>
    </React.Fragment>
  );
};

const Line = styled.hr`
  width: 92%;
`;

export default SummaryDetail;
