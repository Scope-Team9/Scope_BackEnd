// ProjectJoinUser.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid, Text } from "../elements/Index";
import { useHistory } from "react-router";
import ImgType from "../shared/ImgType";

const ProjectJoinUser = (props) => {
  const history = useHistory();
  const goToMypage = (userId) => {
    history.push(`/mypage/${userId}`);
  };
  return (
    <React.Fragment>
      <Grid
        textAlign="center"
        height="120px"
        width="80px"
        margin="0 11px 0 0"
        display="flex"
        justifyContent="center"
      >
        <Grid
          width="60px"
          height="60px"
          borderRadius="50%"
          backgroundColor="#C4C4C4"
          margin="6px 0px"
        >
          <ImgType
            type={props.userPropensityType}
            cursor="pointer"
            _onClick={() => {
              goToMypage(props.userId);
            }}
          ></ImgType>
          <Text size="16px">{props.nickname}</Text>
          <Grid>({props.userPropensityType})</Grid>
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default ProjectJoinUser;
