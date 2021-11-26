// TotalMemberDetail.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid, Text } from "../../../elements/Index";

// TotalMemberDetail의 함수형 컴포넌트를 만든다
const TotalMemberDetail = (props) => {
  return (
    <React.Fragment>
      <Grid display="flex">
        <Text size="18px" bold margin="0px 10px 10px 0px">
          프로젝트 총 인원
        </Text>
        <Text size="18px" color="#9D81F0">
          {props.passedData?.totalMember}명
        </Text>
      </Grid>
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default TotalMemberDetail;
