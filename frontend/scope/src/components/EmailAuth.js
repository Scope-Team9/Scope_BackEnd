import React from "react";
import { Grid, Button, Input, Text } from "../elements/Index";
import styled from "styled-components";
import { Dialog } from "@material-ui/core";
import CloseIcon from "@mui/icons-material/Close";
import { apis } from "../lib/axios";

const EmailAuth = (props) => {
  const [email, setEmail] = React.useState();
  const { modal, setModal } = props;
  const modalClose = () => {
    setModal(false);
  };

  const EmailInput = (data) => {
    setEmail(data);
  };
  const EmailSend = () => {
    const fetchData = async () => {
      const result = await apis.authEmail(email);
      try {
        console.log(result);
        window.alert(result.data.msg);
      } catch (err) {
        console.log(err.response);
      }
    };
    fetchData();
  };
  return (
    <Dialog maxWidth={"sm"} scroll="paper" open={modal}>
      <ModalWrap>
        <Grid>
          {/* 헤더 */}
          <Grid
            width="100%"
            height="13%"
            // bg="#B29CF4"
            position="relative"
            textAlign="center"
            padding="10px 0 10px 0"
          >
            <Grid
              position="absolute"
              top="0px"
              right="20px"
              width="20px"
              padding="10px"
            >
              <CloseIcon fontSize="large" onClick={modalClose} />
            </Grid>
            <Grid margin="20px 0 0 0">
              <Text size="30px" bold color="#08061D">
                이메일 인증
              </Text>
            </Grid>
          </Grid>
          {/* 내용작성 */}
          <Grid height="45%">
            <Dec>이메일 인증을 하고 이메일 알림을 받아 보세요!</Dec>

            <Input
              padding="10px"
              placeholder="이메일을 입력해 주세요."
              margin="10px 0 0 18%"
              fontSize="15px"
              border="1px solid #C9C9C9"
              borderRadius="8px"
              width="320px"
              _onChange={(e) => {
                EmailInput(e.target.value);
              }}
            ></Input>

            <Button
              width="320px"
              height="50px"
              margin="-90px 0 0 18%"
              _onClick={() => {
                EmailSend();
              }}
            >
              인증하기
            </Button>
          </Grid>
        </Grid>
      </ModalWrap>
    </Dialog>
  );
};

const ModalWrap = styled.div`
  width: 500px;
  height: 300px;
  border-radius: 20px;
`;
const Dec = styled.p`
  color: #08061d;
  font-size: 14px;
  align-items: center;
  display: flex;
  justify-content: center;
  margin-top: 25px;
`;

export default EmailAuth;
