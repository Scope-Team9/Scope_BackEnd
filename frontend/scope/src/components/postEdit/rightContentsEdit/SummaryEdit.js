import React from "react";
import { Grid, Text, Input } from "../../../elements/Index";

const SummaryEdit = (props) => {
  return (
    <React.Fragment>
      <Grid margin="10px auto">
        <Text>한줄소개</Text>
        <Input
          width="100%"
          maxLength="60"
          height="40px"
          padding="10px"
          placeholder="프로젝트를 한줄소개를 소개해주세요."
          border="1px solid #C4C4C4"
          inputFocusOutline="none"
          fontSize="16px"
          type="text"
          editValue={props.summary}
          _onChange={(e) => {
            props.setSummary(e.target.value);
          }}
        />
      </Grid>
    </React.Fragment>
  );
};

export default SummaryEdit;
