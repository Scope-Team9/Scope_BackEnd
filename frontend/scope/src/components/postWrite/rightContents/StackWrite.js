// StackWrite.js
/* eslint-disable */

// import를 한다.
import React, { useEffect, useCallback } from "react";
import styled from "styled-components";
import { Grid, Text } from "../../../elements/Index";
import Select from "react-select";

// StackWrite 함수형 컴포넌트를 만든다.
const StackWrite = (props) => {
  const stackSelect = [
    { value: "React", label: "React" },
    { value: "Java", label: "Java" },
    { value: "Javascript", label: "Javascript" },
    { value: "Python", label: "Python" },
    { value: "Nodejs", label: "Nodejs" },
    { value: "Flask", label: "Flask" },
    { value: "cpp", label: "cpp" },
    { value: "Django", label: "Django" },
    { value: "php", label: "php" },
    { value: "Vue", label: "Vue" },
    { value: "Spring", label: "Spring" },
    { value: "Swift", label: "Swift" },
    { value: "Kotlin", label: "Kotlin" },
    { value: "Typescript", label: "Typescript" },
  ];

  const handleChange = useCallback(
    (inputValue, { action, removedValue }) => {
      if (props.techstack.length < 4) {
        props.setTectstack(inputValue);
      } else {
        if (removedValue !== undefined) {
          let temp = props.techstack.filter(
            (item) => item["value"] !== removedValue["value"]
          );
          props.setTectstack(temp);
        } else {
          window.alert("최대 4가지만 선택 가능합니다.");
        }
      }
    },
    [stackSelect]
  );

  const formatTech = () => {
    let tamarray = [];
    let index;
    for (index = 0; index < props.techstack.length; index++) {
      tamarray.push(props.techstack[index]["label"]);
    }
    props.setTechStackList(tamarray);
  };

  useEffect(() => {
    formatTech();
  }, [props.techstack]);

  return (
    <React.Fragment>
      <Grid margin="10px auto">
        <Text size="18px" bold>
          기술스택 선택
          <SubDescription> (5개 이상 중복 X)</SubDescription>
        </Text>
        <Select
          isMulti
          value={props.techstack}
          components={props.animatedComponents}
          styles={props.styles}
          options={stackSelect}
          onChange={handleChange}
          placeholder={<div>기술 스택을 선택해주세요.</div>}
        />
      </Grid>
    </React.Fragment>
  );
};

// styled-components
const SubDescription = styled.span`
  color: rgb(186, 187, 192);
  font-size: 12px;
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default StackWrite;
