// StackDetail.js
/* eslint-disable */

// import를 한다.
import React from "react";
import styled from "styled-components";
import { Grid, Text } from "../../../elements/Index";

// StackDetail의 함수형 컴포넌트를 만든다.
const StackDetail = (props) => {
  return (
    <React.Fragment>
      <Grid display="flex" margin="20px auto">
        <Text size="18px" bold margin="auto 10px auto 0px">
          기술스택
        </Text>
        {props.passedData?.techStack.map((item, index) => {
          return (
            <Text margin="auto 5px" key={index}>
              <StackBox>{item}</StackBox>
            </Text>
          );
        })}
      </Grid>
    </React.Fragment>
  );
};

// styled-components
const StackBox = styled.div`
  color: white;
  background-color: #554475;
  border: 1px solid #554475;
  border-radius: 10px;
  padding: 4px 10px;
  text-align: center;
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default StackDetail;
