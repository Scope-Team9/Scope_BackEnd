import React from "react";
import { Grid, Input, Text, Button } from "../../elements/Index";
import { useDispatch } from "react-redux";
import { applyCreators } from "../../redux/modules/applyProject";
import styled from "styled-components";
import CloseIcon from "@mui/icons-material/Close";

const TeamExit = props => {
  const dispatch = useDispatch();
  const { modalClose, postId } = props;
  const exitTeam = () => {
    const isPostId = {
      postId: postId,
    };
    dispatch(applyCreators.exitTeamAPI(isPostId));
  };
  return (
    <ModalWrap>
      <Grid height="10%" position="relative">
        <Grid
          position="absolute"
          top="0px"
          right="10px"
          width="20px"
          padding="10px"
        >
          <CloseIcon fontSize="large" onClick={modalClose} />
        </Grid>
      </Grid>

      <Grid
        margin="auto"
        height="90%"
        width="320px"
        alignItems="center"
        textAlign="center"
      >
        <Grid height="20%" textAlign="center">
          <Text size="30px" bold>
            팀탈퇴
          </Text>
        </Grid>
        <Grid height="25%" margin="10px 0">
          <Text size="14px">
            정말로 탈퇴하시겠습니까? <br /> 한번 탈퇴하면 다시 신청이 불가능할
            수 있습니다.
          </Text>
        </Grid>
        <Grid height="10%">
          <Button borderRadius="25px" _onClick={exitTeam}>
            팀탈퇴
          </Button>
        </Grid>
      </Grid>
    </ModalWrap>
  );
};

const ModalWrap = styled.div`
  width: 550px;
  height: 300px;
  position: relative;
`;

export default TeamExit;
