/* eslint-disable */
import React from "react";
import styled from "styled-components";
import { Grid, Image, Text, Button } from "../../elements/Index";
import EmailAuth from "../EmailAuth";
const Banners = (props) => {
  //   console.log(props);
  const [myData, setMyData] = React.useState();
  const [arr, setArr] = React.useState([
    {
      id: "LVG",
      name: "호랑이",
      img: "/img/호랑이배너.jpg",
      color: "#eed691",
    },
    {
      id: "LVP",
      name: "늑대",
      img: "/img/늑대배너.jpg",
      color: "#afa9a0",
    },
    {
      id: "LHG",
      name: "여우",
      img: "/img/여우배너.jpg",
      color: "#e4812a",
    },
    {
      id: "LHP",
      name: "팬더",
      img: "/img/팬더배너.jpg",
      color: "#e4812a",
    },
    {
      id: "FVG",
      name: "토끼",
      img: "/img/토끼배너.jpg",
      color: "#998fc9",
    },
    {
      id: "FVP",
      name: "강아지",
      img: "/img/강아지배너.jpg",
      color: "#e8ddb8",
    },
    {
      id: "FHG",
      name: "고양이",
      img: "/img/고양이배너.jpg",
      color: "#6d6e72",
    },
    {
      id: "FHP",
      name: "물개",
      img: "/img/물개배너.jpg",
      color: "#a9adb3",
    },
    {
      id: "RHP",
      name: "너구리",
      img: "/img/너구리배너.jpg",
      color: "#926D41",
    },
  ]);

  React.useEffect(() => {
    arr.map((item) => {
      if (props.type === item.id) {
        setMyData(item);
      }
    });
    // console.log(myData);
  }, []);

  return (
    <React.Fragment>
      {myData && (
        <BannerAnimals color={myData.color}>
          <BannerImg src={myData.img}></BannerImg>
          <Grid
            margin="-600px 0 0 33.5%"
            display="flex"
            height="200px"
            width="100%"
            justifyContent="space-between"
          >
            <WhiteP>
              {myData.id} / {myData.name}
            </WhiteP>

            {/* {props.myPage === true && (
              <Grid alignItems="center" display="flex" justifyContent="center">
                <ConfirmEmail
                  onClick={() => {
                    props.onClick();
                  }}
                >
                  이메일 인증하기
                </ConfirmEmail>
                <EmailAuth modal={props.modal} setModal={props.setModal} />
              </Grid>
            )} */}
          </Grid>
        </BannerAnimals>
      )}
    </React.Fragment>
  );
};

const BannerImg = styled.img`
  object-fit: cover;
`;

const BannerAnimals = styled.div`
  width: 100%;
  background-repeat: no-repeat;
  background-size: cover;

  background-color: ${(props) => (props.color ? props.color : "#white")};
  z-index: 0;
`;

const ConfirmEmail = styled.button`
  width: 160px;
  padding: 8px 20px;
  border: 1px solid white;
  background-color: transparent;
  color: white;
  border-radius: 10px;
  z-index: 99999;
  cursor: pointer;
  &:hover {
    color: black;
    background-color: white;
    opacity: 0.7;
  }

  @media screen and (max-width: 750px) {
    color: black;
  } ;
`;

const WhiteP = styled.p`
  font-size: 30px;
  color: white;
  font-weight: bold;
  width: 180px;
  display: flex;
  justify-content: center;
  align-items: center;
`;
export default Banners;
