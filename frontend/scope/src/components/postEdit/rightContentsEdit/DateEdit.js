// DateEdit.js
/* eslint-disable */

// import를 한다.
import React from "react";
import styled from "styled-components";
import { Grid, Text } from "../../../elements/Index";
import DatePicker from "react-datepicker";
import { ko } from "date-fns/esm/locale";

// DateEdit의 함수형 컴포넌트를 만든다.
const DateEdit = (props) => {
  const startDate = (date) => {
    if (date <= props.endDate) {
      props.setStartdate(date);
    } else {
      window.alert("시작일을 잘못 설정했습니다.");
    }
  };

  const endDate = (data) => {
    props.setEnddate(data);
  };
  return (
    <React.Fragment>
      <Grid>
        <Text size="18px" bold>
          기간설정
        </Text>
        <Grid display="flex" textAlign="center" margin="20px auto">
          {/* 시작 일*/}
          <Grid>
            <Text>프로젝트 시작일</Text>
            <SDatePicker
              dateFormat="yyyy - MM - dd"
              selected={new Date(props.startDate)}
              onChange={startDate}
              startdate={props.startDate}
              selectsStart
              locale={ko}
              minDate={new Date()}
              placeholderText="프로젝트 시작일 입력"
            />
          </Grid>
          {/* 종료 일*/}
          <Grid>
            <Text>프로젝트 종료일</Text>
            <SDatePicker
              dateFormat="yyyy - MM - dd"
              selected={new Date(props.endDate)}
              onChange={endDate}
              startdate={props.startDate}
              enddate={props.endDate}
              selectsEnd
              locale={ko}
              minDate={new Date()}
              placeholderText="프로젝트 종료일 입력"
            />
          </Grid>
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

// styled-components
const SDatePicker = styled(DatePicker)`
  box-sizing: border-box;
  width: 350px;
  height: 40px;
  border-radius: 10px;
  border: 1px solid #c4c4c4;
  color: black;
  font-size: 16px;
  text-align: center;
  margin-top: 0.6rem;
  margin-left: 10px;
  outline: none;
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default DateEdit;
