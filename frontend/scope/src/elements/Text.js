import React from "react";
import styled from "styled-components";

const Text = props => {
  const {
    children,
    color,
    size,
    bold,
    align,
    margin,
    padding,
    underline,
    family,
    border,
    _onClick,
    cursor,
    hover,
  } = props;
  const styles = {
    color,
    size,
    bold,
    align,
    margin,
    padding,
    underline,
    family,
    border,
    cursor,
  };
  return (
    <ElText {...styles} onClick={_onClick}>
      {children}
    </ElText>
  );
};

Text.defaultProps = {
  children: null,
  color: null,
  size: null,
  bold: false,
  align: null,
  underline: null,
  margin: false,
  padding: false,
  family: false, //폰트 타입
  border: null,
  _onClick: () => {},
  cursor: null,
};

const ElText = styled.span`
  color: ${props => props.color};
  font-size: ${props => props.size};
  font-weight: ${props => props.bold};

  text-align: ${props => props.align};
  margin: ${props => props.margin};
  padding: ${props => props.padding};
  family: ${props => props.family};
  border: ${props => props.border};
  cursor: ${props => props.cursor};
  font-family: "GmarketSans";
  line-height: 1.2;
  letter-spacing: -0.0375em;
`;

export default Text;
