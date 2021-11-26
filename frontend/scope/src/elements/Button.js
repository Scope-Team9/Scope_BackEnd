import React from "react";
import styled from "styled-components";

const Button = props => {
  const {
    isId,
    isTest,
    color,
    text,
    _onClick,
    children,
    margin,
    width,
    padding,
    backgroundColor,
    height,
    fontSize,
    borderRadius,
    top,
    bottom,
    left,
    right,
    hover,
    display,
    isValue,
    disabled,
    isChecked,
    hoverBg,
    hoverCl,
    common,
    border,
    isActive,
    zIndex,
  } = props;

  if (isTest) {
    return (
      <>
        <TestButton
          onClick={_onClick}
          value={isValue}
          disabled={disabled}
          isChecked={isChecked}
          id={isId}
          isActive={isActive}
        >
          {text ? text : children}
        </TestButton>
      </>
    );
  }

  if (common) {
    const styles = {
      margin,
      width,
      padding,
      color,
      height,
      fontSize,
      borderRadius,
      top,
      bottom,
      left,
      right,
      border,
      backgroundColor,
    };
    return (
      <>
        <Common
          {...styles}
          onClick={_onClick}
          value={isValue}
          disabled={disabled}
          isChecked={isChecked}
          id={isId}
          isActive={isActive}
        >
          {text ? text : children}
        </Common>
      </>
    );
  }

  const styles = {
    margin,
    width,
    padding,
    backgroundColor,
    color,
    height,
    fontSize,
    borderRadius,
    top,
    bottom,
    left,
    right,
    hover,
    display,
    hoverBg,
    hoverCl,
    zIndex,
  };

  return (
    <>
      <ElButton {...styles} value={isValue} onClick={_onClick} id={isId}>
        {text ? text : children}
      </ElButton>
    </>
  );
};

Button.defaultProps = {
  position: false,
  text: false,
  children: null,
  _onClick: () => {},
  isFloat: false,
  margin: "auto",
  width: "100%",
  padding: "12px 0px",
  color: "white",
  height: "50px",
  top: null,
  bottom: null,
  left: null,
  right: null,
  hover: null,
  display: null,
  isValue: null,
};

const ElButton = styled.button`
  width: ${props => props.width};
  color: ${props => props.color};
  padding: ${props => props.padding};
  height: ${props => props.height};
  font-size: ${props => props.fontSize};
  margin: ${props => props.margin};
  ${props =>
    props.backgroundColor
      ? `background-color:${props.backgroundColor}`
      : "background-color: #554475"};
  box-sizing: border-box;
  border-radius: 5px;
  font-weight: bold;
  ${props => (props.border ? `border:${props.border}` : "border: none")};
  ${props =>
    props.borderRadius
      ? `border-radius:${props.borderRadius}`
      : "border-radius: 25px"};
  cursor: pointer;
  flex-shrink: 0;
  &:hover {
    background-color: ${props => props.hoverBg};
    color: ${props => props.hoverCl};
    transform: translate();
    transition: 0.3s ease-out;
  }
  vertical-align: middle;
  top: ${props => props.top};
  bottom: ${props => props.bottom};
  left: ${props => props.left};
  right: ${props => props.right};
  position: ${props => props.position};
  flex-shrink: 0;
  display: ${props => props.display};
  z-index: ${props => props.zIndex};
  font-family: "GmarketSans";
`;

const FloatButton = styled.div`
  width: 50px;
  height: 50px;
  background-color: #ffffff;
  color: ${props => props.color};
  box-sizing: border-box;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.12), 0 2px 5px rgba(0, 0, 0, 0.24);
  font-size: 36px;
  font-weight: bold;
  position: fixed;
  bottom: 50px;
  right: 16px;
  text-align: center;
  vertical-align: middle;
  border: none;
  border-radius: 50px;
  display: ${props => props.display};
`;

const TestButton = styled.button`
  font-size: 12px;
  text-align: center;
  border: 1px solid #554475;
  border-radius: 25px;
  padding: 20px;
  margin: 5px;
  box-shadow: 0px 2px 2px #ddd;
  font-family: "GmarketSans";
  font-weight: 400;
  background-color: ${props => (props.isActive == true ? "#554475" : "#fff")};
  color: ${props => (props.isActive == true ? "#fff" : "#554475")};

  &:hover {
    background-color: #554475;
    cursor: pointer;
    color: #fff;
    box-shadow: 0px 3px 2px #ddd;
    transform: translate();
    transition: 0.3s ease-out;
  }
  /* &::active {
    box-shadow: 0px 1px 2px #111;
    transform: translateY(10px);
  }
  &::disabled {
    cursor: default;
    opacity: 0.7;
  } */
`;

const Common = styled.button`
  background-color: ${props => (props.isActive == true ? "#554475" : "#fff")};
  height: ${props => props.height};
  width: ${props => props.width};
  color: ${props => (props.isActive == true ? "#fff" : "#554475")};
  margin-right: 3px;
  border-radius: 25px;
  border: 1px solid #554475;
  cursor: pointer;
  top: ${props => props.top};
  bottom: ${props => props.bottom};
  left: ${props => props.left};
  right: ${props => props.right};
  position: ${props => props.position};
  &:hover {
    background-color: #554475;
    color: #fff;
    transform: translate();
    transition: 0.3s ease-out;
  }
  font-family: "GmarketSans";
`;
export default Button;
