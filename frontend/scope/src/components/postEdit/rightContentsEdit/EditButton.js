// EditButton.js
/* eslint-disable */

// import를 한다.
import React from "react";
import styled from "styled-components";
import { Grid } from "../../../elements/Index";

// EditButton의 함수형 컴포넌트를 만든다.
const EditButton = (props) => {
  return (
    <React.Fragment>
      <Grid display="flex" padding="16px">
        <Btn
          onClick={() => {
            props.editHandler();
          }}
        >
          포스트수정 완료
        </Btn>
      </Grid>
    </React.Fragment>
  );
};

// styled-components
const Btn = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 140px;
  height: 35px;
  border: 1px solid #b29cf4;
  border-radius: 50px;
  color: #fff;
  background: white;
  color: #b29cf4;
  margin: 10px auto 10px auto;
  cursor: pointer;
  &:hover {
    color: white;
    background-color: #b29cf4;
    border: 1px solid;
    transition-duration: 1s;
  }
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default EditButton;
