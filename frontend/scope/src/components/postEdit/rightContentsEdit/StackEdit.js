// StackEdit.js
/* eslint-disable */

// import를 한다.
import React, { useEffect, useCallback } from "react";
import { Grid, Text } from "../../../elements/Index";
import Select from "react-select";

// StackEdit의 함수형 컴포넌트를 만든다.
const StackEdit = (props) => {
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

  const orderByLabel = useCallback(
    (a, b) => a.label.localeCompare(b.label),
    []
  );

  const orderOptions = useCallback(
    (values) =>
      values
        .filter((v) => v.isFixed)
        .sort(orderByLabel)
        .concat(values.filter((v) => !v.isFixed).sort(orderByLabel)),
    [orderByLabel]
  );

  const [value, setValue] = React.useState(orderOptions(stackSelect));
  const handleChange = useCallback(
    (inputValue, { action, removedValue }) => {
      switch (action) {
        case "remove-value":
        case "pop-value":
          if (removedValue.isFixed) {
            setValue(orderOptions([...inputValue, removedValue]));

            return;
          }
          break;
        case "clear":
          setValue(stackSelect.filter((v) => v.isFixed));
          return;
        default:
      }

      setValue(inputValue);
      props.setTectstack(inputValue);
    },
    [stackSelect, orderOptions]
  );

  const formatTech = () => {
    let tamarray = [];
    let index;
    for (index = 0; index < props.techstack.length; index++) {
      tamarray.push(props.techstack[index]["label"]);
    }
    props.setTest(tamarray);
  };

  useEffect(() => {
    formatTech();
  }, [props.techstack]);

  return (
    <React.Fragment>
      <Grid margin="20px auto">
        <Text size="18px" bold>
          기술스택 선택
        </Text>
        {/* 1차방안 */}
        <Select
          isMulti
          components={props.animatedComponents}
          isClearable={value.some((v) => !v.isFixed)}
          styles={props.styles}
          value={props.techstack}
          options={stackSelect}
          onChange={handleChange}
          placeholder={<div>기술 스택을 선택해주세요.</div>}
        />
      </Grid>
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default StackEdit;
