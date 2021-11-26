import React from "react";
import { Grid, Input, Text, Button } from "../../elements/Index";
import { useDispatch } from "react-redux";
import { applyCreators } from "../../redux/modules/applyProject";
import styled from "styled-components";
import CloseIcon from "@mui/icons-material/Close";

const SubmitUrl = props => {
  const dispatch = useDispatch();
  const [front, setFront] = React.useState();
  const [back, setBack] = React.useState();
  const { modalClose, postId } = props;

  const submitUrl = () => {
    const github = {
      frontUrl: front,
      backUrl: back,
    };
    dispatch(applyCreators.submitUrlAPI(postId, github));
    modalClose();
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
        // justifyContent="center"
        width="90%"
        alignItems="center"
        textAlign="center"
      >
        <Grid height="10%" textAlign="center">
          <Text size="30px" bold>
            프로젝트주소 소개
          </Text>
        </Grid>
        <Grid height="10%" margin="10px 0">
          <Text size="14px">
            완료된 프로젝트를 소개해주시겠어요? <br />
          </Text>
        </Grid>
        <Grid display="flex" height="40%" width="90%" margin="auto">
          <Grid
            display="flex"
            flexDirection="column"
            height="80%"
            width="30%"
            margin="auto"
            bg="#eee"
          >
            <Grid display="flex" alignItems="center" justifyContent="center">
              <Text>프론트엔드</Text>
            </Grid>
            <Grid
              display="flex"
              alignItems="center"
              justifyContent="center"
              bg="#eee"
            >
              <Text>백엔드</Text>
            </Grid>
          </Grid>
          <Grid
            display="flex"
            flexDirection="column"
            height="80%"
            width="70%"
            margin="auto"
            bg="#eee"
            alignItems="center"
          >
            <Input
              height="100%"
              _onChange={e => {
                console.log(e.target.value);
                setFront(e.target.value);
              }}
            ></Input>

            <Input
              height="100%"
              _onChange={e => {
                console.log(e.target.value);
                setBack(e.target.value);
              }}
            ></Input>
          </Grid>
        </Grid>
        <Grid display="flex" height="15%" margin="20px auto" width="90%">
          <Grid>
            <Button borderRadius="25px" _onClick={submitUrl}>
              제출하기
            </Button>
          </Grid>
          <Grid>
            <Button borderRadius="25px" _onClick={() => {}}>
              다음에제출
            </Button>
          </Grid>
        </Grid>
      </Grid>
    </ModalWrap>
  );
};

const ModalWrap = styled.div`
  width: 550px;
  height: 400px;
  position: relative;
`;

export default SubmitUrl;
