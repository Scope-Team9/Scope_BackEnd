import { NoEncryption } from "@material-ui/icons";
import React from "react";
import styled from "styled-components";
import { Grid } from "../elements/Index";
const ImgType = props => {
  const {
    margin,
    width,
    height,
    _onClick,
    cursor,
    object_fit,
    position,
    right,
    bottom,
  } = props;
  const styles = {
    margin,
    width,
    height,
    cursor,
    object_fit,
    position,
    right,
    bottom,
  };
  const [imges, setImges] = React.useState([
    {
      id: "tiger",
      type: "LVG",
      img: "/img/호랑이.png",
    },
    {
      id: "wolf",
      type: "LVP",
      img: "/img/늑대.png",
    },
    {
      id: "fox",
      type: "LHG",
      img: "/img/여우.png",
    },
    {
      id: "panda",
      type: "LHP",
      img: "/img/판다.png",
    },
    {
      id: "rabbit",
      type: "FVG",
      img: "/img/토끼.png",
    },
    {
      id: "dog",
      type: "FVP",
      img: "/img/개.png",
    },
    {
      id: "cat",
      type: "FHG",
      img: "/img/고양이.png",
    },
    {
      id: "seal",
      type: "FHP",
      img: "/img/물개.png",
    },
    {
      id: "raccoons",
      type: "RHP",
      img: "/img/너구리.png",
    },
  ]);
  const [result, setResult] = React.useState();
  React.useEffect(() => {
    console.log("여기좀 보슈", props.type);
    imges.map(item => {
      if (item.type === props.type) {
        setResult(item.img);
        return item;
      }
    });
  }, []);

  return (
    <>
      {result && (
        <Img key={imges.id} {...styles} src={result} onClick={_onClick} />
      )}
    </>
  );
};

ImgType.defaultProps = {
  margin: "6px",
  width: "48px",
  height: "48px",
  object_fit: null,
  position: null,
  right: null,
  bottom: null,
  _onClick: () => {},
};

const Img = styled.img`
  width: ${props => props.width};
  height: ${props => props.height};
  margin: ${props => props.margin};
  cursor: ${props => props.cursor};
  object-fit: ${props => props.object_fit};
  position: ${props => props.position};
  right: ${props => (props.src === "LVP" ? "0px" : props.right)};
  bottom: ${props => (props.src === "LVP" ? "50px" : null)};
`;
export default ImgType;
