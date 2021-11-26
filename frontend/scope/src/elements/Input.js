import React from "react";
import styled from "styled-components";
import { Grid, Text } from "./Index";

const Input = props => {
  const {
    id,
    label,
    placeholder,
    _onChange,
    onSubmit,
    type,
    multiLine,
    edit,
    value,
    editValue,
    defaulValue,
    margin,
    width,
    padding,
    height,
    border,
    borderbottom,
    maxLength,
    borderRadius,
    bg,
    backgroundImage,
    backgroundColor,
    innerRef,
    inputFocusOutline,
    inputFocusBorder,
    inputFocusBoxShadow,
    fontSize,
  } = props;

  const styles = {
    padding,
    height,
    border,
    borderbottom,
    borderRadius,
    bg,
    maxLength,
    backgroundImage,
    inputFocusOutline,
    inputFocusBorder,
    inputFocusBoxShadow,
    fontSize,
  };
  if (multiLine) {
    return (
      <Grid>
        {label && <Text margin="0px">{label}</Text>}
        <ElTextarea
          backgroundColor={backgroundColor}
          value={value}
          defaulValue={defaulValue}
          rows={10}
          maxLength={maxLength}
          placeholder={placeholder}
          onChange={_onChange}
        ></ElTextarea>
      </Grid>
    );
  }

  if (edit) {
    return (
      <Grid>
        {label && <Text margin="0px">{label}</Text>}
        <ElInput
          {...styles}
          backgroundColor={backgroundColor}
          width={width}
          margin={margin}
          type={type}
          value={value}
          defaulValue={defaulValue}
          placeholder={placeholder}
          onChange={_onChange}
          maxLength={maxLength}
          ref={innerRef}
          onKeyPress={e => {
            console.log(e.key);
            if (e.key === "Enter") {
              console.log("pass");
              onSubmit(e);
            }
          }}
        />
      </Grid>
    );
  }

  return (
    <Grid>
      {label && <Text margin="0px">{label}</Text>}
      <ElInput
        {...styles}
        backgroundColor={backgroundColor}
        width={width}
        margin={margin}
        type={type}
        placeholder={placeholder}
        value={editValue}
        maxLength={maxLength}
        defaulValue={defaulValue}
        onChange={_onChange}
        ref={innerRef}
        onKeyPress={e => {
          console.log(e.key);
          if (e.key === "Enter") {
            console.log("pass");
            onSubmit(e);
          }
        }}
      />
    </Grid>
  );
};

Input.defaultProps = {
  multiLine: false,
  label: false,
  placeholder: "텍스트를 입력해주세요.",
  type: "text",
  value: "",
  defaulValue: "",
  margin: 0,
  padding: false,
  width: "100%",
  height: false,
  border: false,
  borderbottom: false,
  borderRadius: false,
  bg: false,
  backgroundImage: false,
  is_submit: false,
  _onChange: () => {},
  onSubmit: () => {},
};

const ElTextarea = styled.textarea`
  margin: ${props => props.margin};
  border: ${props => props.border};
  width: ${props => props.width};
  height: ${props => props.height};
  border-radius: 25px;
  border-radius: ${props => props.borderRadius};
  background: ${props => props.bg};
  padding: ${props => props.padding};
  background-image: ${props => props.backgroundImage};
  box-sizing: border-box;
  background-color: ${props => props.backgroundColor};
  font-family: "GmarketSans";
`;

const ElInput = styled.input`
  margin: ${props => props.margin};
  border: ${props => props.border};
  width: ${props => props.width};
  height: ${props => props.height};
  text-align: ${props => props.textAlign};
  border-radius: ${props => props.borderRadius};
  border-bottom: ${props => props.borderBottom};
  background: ${props => props.bg};
  padding: ${props => props.padding};
  background-image: ${props => props.backgroundImage};
  box-sizing: border-box;

  background-color: ${props => props.backgroundColor};
  font-size: ${props => props.fontSize};
  &:focus {
    outline: ${props => props.inputFocusOutline};
    border: ${props => props.inputFocusBorder};
    box-shadow: ${props => props.inputFocusBoxShadow};
  }
  font-family: "GmarketSans";
`;

export default Input;
