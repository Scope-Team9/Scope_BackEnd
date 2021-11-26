// AddPost.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid } from "../elements/Index";
import LeftBanner from "../components/postWrite/LeftBanner";
import RightWrite from "../components/postWrite/RightWrite";

// WritePost의 함수형 컴포넌트를 만든다.
const WritePost = (props) => {
  return (
    <React.Fragment>
      <Grid
        display="flex"
        justifyContent="center"
        border="1px solid #C4C4C4"
        maxWidth="1920px"
        margin="auto"
      >
        <LeftBanner />
        <RightWrite />
      </Grid>
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default WritePost;
