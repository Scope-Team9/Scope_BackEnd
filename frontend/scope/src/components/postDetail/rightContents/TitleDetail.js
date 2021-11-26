// TitleDetail.js
/* eslint-disable */

// import를 한다.
import React from "react";
import styled from "styled-components";
import { Grid, Text } from "../../../elements/Index";

// TitleDetail의 함수형 컴포넌트를 만든다.
const TitleDetail = (props) => {
  return (
    <React.Fragment>
      <Grid margin="40px auto auto">
        <Text size="40px" bold>
          {props.passedData?.title}
          <Line />
        </Text>
      </Grid>
    </React.Fragment>
  );
};

// styled-components
const Line = styled.hr`
  width: 100%;
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default TitleDetail;
