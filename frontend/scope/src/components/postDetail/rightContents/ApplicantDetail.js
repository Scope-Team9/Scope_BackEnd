// ApplicantDetail.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid, Text } from "../../../elements/Index";
import ProjectJoinUser from "../../ProjectJoinUser";

const ApplicantDetail = (props) => {
  return (
    <React.Fragment>
      <Grid margin="4px 0px 0px 0px">
        <Grid width="100%" display="flex">
          <Grid width="80px" textAlign="center">
            <Text size="18px" bold>
              게시자
            </Text>
          </Grid>

          <Grid width="80px" margin="0 0 0 10px" textAlign="center">
            <Text size="18px" bold>
              참여자
            </Text>
          </Grid>
        </Grid>

        <Grid display="flex" margin="6px 0px 0px 0px">
          {props.passdedMenber?.map((item) => (
            <ProjectJoinUser key={item.userId} {...item} />
          ))}
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default ApplicantDetail;
