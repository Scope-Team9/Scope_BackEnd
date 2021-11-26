// TitleDetail.js
/* eslint-disable */

// import를 한다.
import React from "react";
import styled from "styled-components";
import { Grid, Text } from "../../../elements/Index";

// ContentDetail의 함수형 컴포넌트를 만든다.
const ContentDetail = (props) => {
  return (
    <React.Fragment>
      <Grid margin="10px auto auto">
        <Text size="18px" bold margin="0px 10px 0px 0px">
          프로젝트 설명
        </Text>
        <Content>{props.passedData?.contents}</Content>
      </Grid>
    </React.Fragment>
  );
};

// styled-components
const Content = styled.h4`
  width: 100%;
  height: 340px;
  padding: 10px;
  border: 1px solid #c4c4c4;
  border-radius: 5px;
  overflow: scroll;
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default ContentDetail;
