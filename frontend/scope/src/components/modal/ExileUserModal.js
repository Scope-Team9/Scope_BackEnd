/* eslint-disable */
import React from "react";
import { Grid, Input, Text, Button, Image } from "../../elements/Index";
import { Dialog } from "@material-ui/core";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { applyCreators } from "../../redux/modules/applyProject";
import CloseIcon from "@mui/icons-material/Close";
import { apis } from "../../lib/axios";
const ApplyStatusModal = props => {
  const dispatch = useDispatch();
  const applyUsers = useSelector(state => state.apply.applyUsers);
  const [applyedUsers, setApplyUsers] = React.useState();
  const [acceptButton, setAcceptButton] = React.useState();
  const { applyStatusModal, setApplyStatusModal, postId } = props;

  const modalClose = () => {
    setApplyStatusModal(false);
  };

  React.useEffect(() => {
    console.log(applyedUsers);
    const fetchData = async () => {
      try {
        const result = await apis.serachTeamUser(postId);
        console.log(result);
        setApplyUsers(result.data.data);
      } catch (err) {
        console.log(err);
      }
    };
    fetchData();

    // dispatch(applyCreators.applyUserAPI(postId));
  }, [applyStatusModal, acceptButton]);

  const exile = userId => {
    console.log(userId);
    const fetchData = async () => {
      try {
        const result = await apis.exileUser(postId, userId);
        console.log(result);
        setAcceptButton(result);
      } catch (err) {
        console.log(err.response);
      }
    };
    fetchData();
    // dispatch(applyCreators.acceptOfferAPI(postId, acceptInfo));
  };

  return (
    <>
      {applyedUsers && (
        <Dialog
          maxWidth={"sm"}
          scroll="paper"
          open={applyStatusModal}
          onClose={modalClose}
        >
          <ModalWrap>
            <Grid
              height="10%"
              bg="#B29CF4"
              position="relative"
              textAlign="center"
              padding="10px 0 0 0"
            >
              <Grid
                position="absolute"
                top="0px"
                right="10px"
                width="20px"
                padding="10px"
              >
                <CloseIcon fontSize="large" onClick={modalClose} />
              </Grid>
              <Text size="30px" bold color="#fff">
                신청현황
              </Text>
            </Grid>
            {applyedUsers == "" && (
              <Grid height="0%" justifyContent="center">
                <Grid
                  justifyContent="center"
                  alignItems="center"
                  width="70%"
                  textAlign="center"
                  margin="auto"
                >
                  <Grid height="50%">
                    <img width="100%" src="/img/step9.png" />
                  </Grid>
                  <Grid margin="250px 0">지원자가 아직 없습니다!</Grid>
                </Grid>
              </Grid>
            )}

            <Grid display="flex" height="85%" justifyContent="center">
              <Grid width="90%" margin="10px 0">
                {applyedUsers.map((user, idx) => (
                  <Grid
                    margin="10px auto"
                    height="100px"
                    display="flex"
                    alignItems="center"
                    justifyContent="space-around"
                    padding="10px"
                    width="90%"
                    key={user.userId}
                    {...user}
                  >
                    <Grid margin="auto" width="30%">
                      {applyedUsers[idx].userPropensityType === "LVG" && (
                        <UserImg src="/img/호랑이.png"></UserImg>
                      )}
                      {applyedUsers[idx].userPropensityType === "LVP" && (
                        <UserImg src="/img/늑대.png"></UserImg>
                      )}
                      {applyedUsers[idx].userPropensityType === "LHG" && (
                        <UserImg src="/img/여우.png"></UserImg>
                      )}
                      {applyedUsers[idx].userPropensityType === "LHP" && (
                        <UserImg src="/img/판다.png"></UserImg>
                      )}
                      {applyedUsers[idx].userPropensityType === "FVG" && (
                        <UserImg src="/img/토끼.png"></UserImg>
                      )}
                      {applyedUsers[idx].userPropensityType === "FVP" && (
                        <UserImg src="/img/허스키.png"></UserImg>
                      )}
                      {applyedUsers[idx].userPropensityType === "FHG" && (
                        <UserImg src="/img/고양이.png"></UserImg>
                      )}
                      {applyedUsers[idx].userPropensityType === "FHP" && (
                        <UserImg src="/img/물개.png"></UserImg>
                      )}
                    </Grid>
                    <Grid height="100%" width="80%">
                      <Grid display="flex" height="60%" margin="auto">
                        <Grid
                          margin="auto"
                          height="50px"
                          display="flex"
                          justifyContent="space-between"
                        >
                          <Grid height="100%" textAlign="center">
                            <Grid bg="#eee" height="50%">
                              닉네임
                            </Grid>
                            <Grid bg="#aaa" height="50%">
                              타입
                            </Grid>
                          </Grid>
                          <Grid margin="auto" height="100%" textAlign="center">
                            <Grid height="50%">
                              {applyedUsers[idx].nickname}
                            </Grid>
                            <Grid height="50%">
                              {applyedUsers[idx].userPropensityType}
                            </Grid>
                          </Grid>
                        </Grid>
                        <Grid margin="auto" height="50px" width="80%">
                          <Button
                            common
                            isValue={applyedUsers[idx].userId}
                            _onClick={e => {
                              window.confirm("추방하시겠습니까?");
                              exile(e.target.value);
                            }}
                          >
                            추방하기
                          </Button>
                        </Grid>
                        <Grid
                          margin="auto auto auto 3px"
                          height="50px"
                          width="80%"
                        ></Grid>
                      </Grid>
                    </Grid>
                  </Grid>
                ))}
              </Grid>
            </Grid>
          </ModalWrap>
        </Dialog>
      )}
    </>
  );
};

const ModalWrap = styled.div`
  width: 550px;
  height: 500px;
`;
const CommentBubble = styled.div`
  position: relative;
  background: #f1f9ff;
  height: 40%;
  /* border: #b29cf4 solid 1px; */
  border-radius: 10px;
  padding: 0 12px;
  ::after {
    content: "";
    position: absolute;
    border-style: solid;
    border-width: 10px 15px 0;
    border-color: #f1f9ff transparent;
    display: block;
    width: 0;
    z-index: 1;
    top: 10px;
    left: -15px;
  }
`;

const UserImg = styled.img`
  object-fit: cover;
  width: 100px;
  border-radius: 12px;
  background-color: #ececec;
  cursor: pointer;
`;

export default ApplyStatusModal;
