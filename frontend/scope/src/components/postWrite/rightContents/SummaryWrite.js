import React from "react";
import { Grid, Text, Input } from "../../../elements/Index";

const SummaryWrite = (props) => {
  return (
    <React.Fragment>
      <Grid margin="20px auto">
        <Text size="18px" bold>
          한줄소개
        </Text>
        <Input
          width="100%"
          height="40px"
          padding="10px"
          margin="4px auto"
          border="1px solid #C4C4C4"
          borderRadius="10px"
          placeholder="프로젝트를 한줄소개를 소개해주세요."
          maxLength="60"
          inputFocusOutline="none"
          fontSize="16px"
          _onChange={(e) => {
            props.setSummary(e.target.value);
          }}
        ></Input>
      </Grid>
    </React.Fragment>
  );
};

export default SummaryWrite;
