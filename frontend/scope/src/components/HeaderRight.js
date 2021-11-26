/* eslint-disable */
import React from "react";
import styled from "styled-components";
import { history } from "../redux/configureStore";

// import { useHistory } from "react-router";
import { useDispatch, useSelector } from "react-redux";
import { Grid, Text, Image, Button } from "../elements/Index";
import LoginModal from "./LoginModal";
import { userCreators } from "../redux/modules/user";
import { deleteCookie } from "../shared/Cookie";

const HeaderRight = (props) => {
  const dispatch = useDispatch();
  const isToken = document.cookie;
  // const history = useHistory();
  const userInfo = useSelector((state) => state.user);
  console.log(userInfo);
  const [showModal, setShowModal] = React.useState(false);
  const sigunupModalState = useSelector(
    (state) => state.user.sigunupModalState
  );
  const modalOpen = () => {
    setShowModal(true);
  };

  const logOut = () => {
    deleteCookie("ScopeUser");
    dispatch(userCreators.logOut());
    history.replace("/");
  };

  if (isToken) {
    return (
      <Grid
        display="flex"
        justifyContent="space-around"
        alignItems="center"
        height="auto"
        width="auto"
      >
        <HeaderWrapper>
          {/*           
          <Message
            onClick={() => {
              history.push(`/message`);
            }}
          >
            <i class="far fa-envelope"></i>
          </Message>
          
          <Bell>
            <i class="far fa-bell"></i>
          </Bell> */}
          <IconWrap>
            <Grid
              display="flex"
              alignItems="center"
              margin="0 20px"
              _onClick={() => {
                history.push(`/mypage/${userInfo.userId}`);
              }}
            >
              {userInfo.userPropensityType === "LVG" && (
                <UserImg size="50" src="/img/호랑이.png"></UserImg>
              )}
              {userInfo.userPropensityType === "LVP" && (
                <UserImg src="/img/늑대.png"></UserImg>
              )}
              {userInfo.userPropensityType === "LHG" && (
                <UserImg src="/img/여우.png"></UserImg>
              )}
              {userInfo.userPropensityType === "LHP" && (
                <UserImg src="/img/판다.png"></UserImg>
              )}
              {userInfo.userPropensityType === "FVG" && (
                <UserImg src="/img/토끼.png"></UserImg>
              )}
              {userInfo.userPropensityType === "FVP" && (
                <UserImg src="/img/개.png"></UserImg>
              )}
              {userInfo.userPropensityType === "FHG" && (
                <UserImg src="/img/고양이.png"></UserImg>
              )}
              {userInfo.userPropensityType === "FHP" && (
                <UserImg src="/img/물개.png"></UserImg>
              )}
              {userInfo.userPropensityType === "RHP" && (
                <UserImg src="/img/너구리.png"></UserImg>
              )}
            </Grid>
          </IconWrap>
          <ButtonWrap>
            <Button
              common
              width="120px"
              text="로그아웃"
              _onClick={logOut}
            ></Button>
          </ButtonWrap>
        </HeaderWrapper>
      </Grid>
    );
  } else {
    return (
      <Grid
        display="flex"
        justifyContent="space-around"
        alignItems="center"
        height="auto"
      >
        <HeaderWrapper>
          <Button
            common
            width="120px"
            text="로그인"
            _onClick={modalOpen}
          ></Button>
          <LoginModal showModal={showModal} setShowModal={setShowModal} />
        </HeaderWrapper>
      </Grid>
    );
  }
};

const HeaderWrapper = styled.div`
  display: flex;
  width: 100%;
  padding-right: 0px;
  justify-content: space-evenly;
  @media screen and (max-width: 595px) {
    width: 20%;
  } ;
`;

const Message = styled.div`
  font-size: 30px;
  margin-top: 8px;
  margin-right: 16px;
  cursor: pointer;
`;
const Bell = styled.div`
  margin-top: 8px;
  font-size: 30px;
  cursor: pointer;
`;

const ButtonWrap = styled.div`
  @media screen and (max-width: 595px) {
    display: none;
  } ;
`;

const IconWrap = styled.div`
  @media screen and (max-width: 595px) {
    width: 90%;
  } ;
`;

const UserImg = styled.img`
  object-fit: cover;
  width: 50px;
  border-radius: 50px;
  box-shadow: 0 0 3px #ddd;
  cursor: pointer;
`;

export default HeaderRight;
