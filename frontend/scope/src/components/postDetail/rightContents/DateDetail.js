// DateDetail.js
/* eslint-disable */

// import를 한다.
import React from "react";
import styled from "styled-components";
import { Grid, Text } from "../../../elements/Index";

// DateDetail의 함수형 컴포넌트를 만든다.
const DateDetail = (props) => {
  return (
    <React.Fragment>
      <Grid display="flex" margin="20px auto">
        <Text size="18px" bold margin="auto 10px auto 0px">
          프로젝트 기간
        </Text>
        <Text>
          <DateBox>
            {props.passedData?.startDate} ~ {props.passedData?.endDate}
          </DateBox>
        </Text>
      </Grid>
    </React.Fragment>
  );
};

const DateBox = styled.div`
  color: black;
  padding: 4px 10px;
  border: 1px solid #e6ddf2;
  border-radius: 10px;
  text-align: center;
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default DateDetail;
