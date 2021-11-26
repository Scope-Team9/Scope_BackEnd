// TitleEdit.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid, Text, Input } from "../../../elements/Index";

// TitleEdit의 함수형 컴포넌트를 만든다.
const TitleEdit = (props) => {
  return (
    <React.Fragment>
      <Grid>
        <Text size="18px" bold>
          제목
        </Text>
        <Input
          width="100%"
          maxLength="35"
          height="40px"
          padding="10px"
          margin="4px auto"
          border="1px solid #C4C4C4"
          borderRadius="10px"
          placeholder="제목을 입력해주세요."
          inputFocusOutline="none"
          fontSize="16px"
          type="text"
          editValue={props.title}
          _onChange={(e) => {
            props.setTitle(e.target.value);
          }}
        />
      </Grid>
    </React.Fragment>
  );
};

// export
export default TitleEdit;
