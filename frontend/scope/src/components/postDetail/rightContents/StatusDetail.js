// StatusDetail.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid, Text } from "../../../elements/Index";

// StatusDetail의 함수형 컴포넌트를 만든다.
const StatusDetail = (props) => {
  return (
    <React.Fragment>
      <Grid display="flex">
        <Text size="18px" bold margin="auto 10px auto 0px">
          프로젝트 상태
        </Text>
        <Text size="18px" color="#9D81F0">
          {props.passedData?.projectStatus}
        </Text>
      </Grid>
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default StatusDetail;
