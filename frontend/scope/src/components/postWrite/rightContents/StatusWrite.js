import React from "react";
import { Grid, Text } from "../../../elements/Index";

import Select from "react-select";

const StatusWrite = (props) => {
  const projectstatus = [{ value: "모집중", label: "모집중" }];

  return (
    <React.Fragment>
      <Grid margin="20px auto">
        <Text size="18px" bold>
          프로젝트 상태체크
        </Text>
        <Select
          options={projectstatus}
          styles={props.styles}
          onChange={(e) => {
            let a;
            a = e["label"];
            props.setProjectstatus(a);
          }}
          placeholder={<div>상태를 설정해주세요.</div>}
        ></Select>
      </Grid>
    </React.Fragment>
  );
};

export default StatusWrite;
