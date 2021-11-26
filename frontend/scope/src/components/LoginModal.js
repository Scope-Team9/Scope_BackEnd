import React, { useState } from "react";
import { Grid, Input, Text, Button, Image } from "../elements/Index";
import { Dialog } from "@material-ui/core";
import { useDispatch, useSelector } from "react-redux";
import styled from "styled-components";
import { userCreators } from "../redux/modules/user";
import Select from "react-select";

import PropensityTest from "./propensityTest/PropensityTest";
import CloseIcon from "@mui/icons-material/Close";

const LoginModal = props => {
  const dispatch = useDispatch();
  const userInfo = useSelector(state => state.user);
  const sigunupModalState = useSelector(state => state.user.sigunupModalState);

  var regExpNick = /^[a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]{2,5}$/;
  var regExpEmail =
    /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

  //테크스택 옵션
  const techStackOption = [
    { value: "React", label: "React" },
    { value: "Java", label: "Java" },
    { value: "Spring", label: "Spring" },
    { value: "JavaScript", label: "JavaScript" },
    { value: "Python", label: "Python" },
    { value: "Node", label: "Node" },
    { value: "cpp", label: "C++" },
    { value: "Flask", label: "Flask" },
    { value: "Django", label: "Django" },
    { value: "Vue", label: "Vue" },
    { value: "php", label: "php" },
    { value: "Swift", label: "Swift" },
    { value: "Kotlin", label: "Kotlin" },
    { value: "TypeScript", label: "TypeScript" },
  ];

  //모달
  const { showModal, setShowModal } = props;

  const TestClose = () => {
    setShowModal(false);
  };
  const modalClose = () => {
    setShowModal(false);
  };

  //입력부분
  const [nickName, setNickName] = useState();
  const [email, setEmail] = useState(userInfo.email);
  const [techStack, setTeckstack] = useState([]);
  const [emailDup, setEmailDup] = useState(false);
  const [nameDup, setNameDup] = useState(false);
  const [test, setTest] = useState(false);

  console.log("닉네임", nickName);
  console.log("이메일", email);
  console.log("기술스택", techStack);
  console.log("sns아이디", userInfo.snsId);

  //닉네임 체크 미들웨어
  const nickCheck = nickName => {
    if (nickName === undefined) {
      alert("닉네임을 입력 해주세요.");
      return false;
    }

    if (!regExpNick.test(nickName)) {
      alert("닉네임은 2~5자 숫자 조합만 가능합니다.");
      return false;
    }
    dispatch(userCreators.nickCheckMiddleWare(nickName));
  };

  //이메일 체크 미들웨어
  const emailCheck = email => {
    if (nickName === "") {
      alert("이메일을 입력 해주세요.");
      return false;
    }

    if (!regExpEmail.test(email)) {
      alert("이메일 형식을 확인해주세요.");
      return false;
    }
    dispatch(userCreators.emailCheckMiddleWare(email));
  };

  //테스트 마친 회원가입 api
  const preSignUP = () => {
    if (techStack.length === 0) {
      alert("기술스택을 선택 해주세요.");
      return false;
    }
    // if (emailDup === false) {
    //   alert("이메일 중복확인을 해주세요.");
    //   return false;
    // }
    if (nameDup === false) {
      alert("닉네임 중복확인을 해주세요.");
      return false;
    }
    const registerInfo = {
      snsId: userInfo.snsId,
      email: userInfo.email,
      nickName: nickName,
      techStack: techStack,
    };
    console.log(registerInfo);
    dispatch(userCreators.testUserMiddleWare(registerInfo));
    setTest(true);
  };

  const customStyles = {
    control: styles => ({
      ...styles,
      backgroundColor: "white",
      borderRadius: "20px",
    }),
    multiValue: (styles, { data }) => ({
      ...styles,
      color: data.color,
      backgroundColor: "#554475",
      color: "white",
      borderRadius: "20px",
    }),
    multiValueLabel: (styles, { data }) => ({
      ...styles,
      color: data.color,
    }),
    multiValueRemove: (styles, { data }) => ({
      ...styles,
      color: data.color,
      ":hover": {
        backgroundColor: data.color,
        color: "white",
      },
    }),
  };
  //회원가입이 필요한 유저일경우 모달창 활성화
  React.useEffect(() => {
    if (sigunupModalState) {
      setShowModal(true);
    }
  }, [sigunupModalState]);

  //회원이 아닐경우 회원가입, 회원일 경우 메인으로 이동
  if (sigunupModalState == true) {
    return (
      <Dialog
        maxWidth={"sm"}
        scroll="paper"
        open={showModal}
        // onClose={modalClose}
      >
        <ModalWrap>
          {/* 테스트가 필요한경우 */}
          {!test ? (
            <Grid>
              {/* 헤더 */}
              <Grid
                height="15%"
                bg="#17334A"
                position="relative"
                textAlign="center"
                padding="10px 0 10px 0"
              >
                <Grid
                  position="absolute"
                  top="0px"
                  right="10px"
                  width="20px"
                  padding="10px"
                >
                  <CloseIcon
                    sx={{ color: "#fff", fontSize: 35 }}
                    onClick={modalClose}
                    cursor="pointer"
                  />
                </Grid>
                <Grid
                  display="flex"
                  justifyContent="center"
                  alignItems="center"
                  height="40px"
                >
                  <Text size="20px" bold color="#fff">
                    회원가입
                  </Text>
                </Grid>
              </Grid>
              {/* 타이틀 */}
              <Grid textAlign="center" margin="40px 0 10px 0">
                <Text bold size="33px">
                  Welcome to Scope!
                </Text>
              </Grid>
              {/* 입력부분 */}
              <Grid
                display="flex"
                justifyContent="center"
                height="10%"
                textAlign="center"
                padding="10px 0"
                margin="auto"
              >
                <Grid width="90%" height="70%" display="flex">
                  {/* 라벨 */}
                  <Grid
                    width="15%"
                    display="flex"
                    flexDirection="column"
                    justifyContent="top"
                    margin="5px auto"
                    height="280px"
                  >
                    <Grid
                      height="29%"
                      display="flex"
                      alignItems="center"
                      justifyContent="center"
                      margin="20px 0 10px 0"
                    >
                      <Text color="#111">닉네임</Text>
                    </Grid>
                    <Grid padding="10px 0 0 0">
                      <Text color="#111">기술스택</Text>
                    </Grid>
                  </Grid>
                  {/* 입력부분 */}
                  <Grid
                    width="60%"
                    display="flex"
                    flexDirection="column"
                    justifyContent="top"
                    margin="15px auto"
                    height="280px"
                  >
                    <Grid height="14%" margin="16px 0">
                      <Input
                        borderRadius="25px"
                        border="1px solid #ddd"
                        fontSize="16px"
                        padding="0 0 0 23px"
                        height="100%"
                        placeholder="닉네임을 입력해주세요"
                        _onChange={e => {
                          setNickName(e.target.value);
                        }}
                      >
                        닉네임
                      </Input>
                    </Grid>
                    <Grid height="40%" padding="0 0 10px 0">
                      <Select
                        styles={customStyles}
                        placeholder="보유중인 기술을 선택해주세요!"
                        isMulti
                        name="techStack"
                        options={techStackOption}
                        className="basic-multi-select"
                        classNamePrefix="select"
                        onChange={e => {
                          let techStack = [];
                          let arr = e;
                          let idx = 0;
                          for (idx = 0; idx < e.length; idx++) {
                            techStack.push(arr[idx]["value"]);
                          }
                          setTeckstack(techStack);
                        }}
                      >
                        기술스택
                      </Select>
                    </Grid>
                  </Grid>
                  {/* 중복체크 */}
                  <Grid
                    width="20%"
                    display="flex"
                    flexDirection="column"
                    justifyContent="center"
                    margin="10px auto"
                    height="280px"
                  >
                    {/* <Grid height="20%" margin="0 0 22px 0">
                      <Button
                        height="52px"
                        fontSize="12px"
                        text="이메일 중복"
                        _onClick={() => {
                          emailCheck(email);
                          setEmailDup(true);
                        }}
                      ></Button>
                    </Grid> */}
                    <Grid height="85%">
                      <Button
                        height="38px"
                        fontSize="12px"
                        text="닉네임 중복"
                        _onClick={() => {
                          nickCheck(nickName);
                          setNameDup(true);
                        }}
                      ></Button>
                    </Grid>
                  </Grid>
                </Grid>
              </Grid>
              {/* 버튼 */}
              <Grid width="50%" margin="auto">
                <Button
                  text="성향테스트"
                  margin="30px 0"
                  _onClick={() => {
                    preSignUP();
                  }}
                ></Button>
              </Grid>
            </Grid>
          ) : (
            <PropensityTest modalClose={TestClose} />
          )}
          <Grid display="flex" justifyContent="center" margin="10px 0 30px 0">
            <Grid width="20%" backgroundColor="#554475" height="3px"></Grid>
          </Grid>
        </ModalWrap>
      </Dialog>
    );
  } else {
    return (
      <Dialog maxWidth={"md"} scroll="paper" open={showModal}>
        <ModalWrap>
          <Grid
            height="15%"
            bg="#554475"
            width
            position="relative"
            padding="10px 0 10px 0"
          >
            <Grid
              position="absolute"
              top="0px"
              right="4%"
              width="3%"
              padding="10px"
            >
              <CloseIcon
                sx={{ color: "#fff", fontSize: 35 }}
                onClick={modalClose}
                cursor="pointer"
              />
            </Grid>
            <Grid
              display="flex"
              justifyContent="center"
              alignItems="center"
              height="40px"
            >
              <Text size="25px" bold color="#fff">
                로그인
              </Text>
            </Grid>
          </Grid>
          <Grid height="80%" padding="20px 0">
            <Grid
              display="flex"
              flexDirection="column"
              alignItems="center"
              position="relative"
              justifyContent="center"
            >
              <Grid margin="20px" display="flex" justifyContent="center">
                <img width="40%" src="/img/호랑이.png" />
              </Grid>
              <Text size="30px" bold="800" margin="0 0 30px 0">
                Welcome to Scope!
              </Text>
              <Grid display="flex" flexDirection="column">
                <GithubBtn
                  onClick={() => {
                    setShowModal(true);
                    window.location.href =
                      //s3
                      // "https://github.com/login/oauth/authorize?client_id=5bb2c0fab941fb5b8f9f&scope=repo:status read:repo_hook user:email&redirect_uri=http://kbumsoo.s3-website.ap-northeast-2.amazonaws.com/user/github/callback";

                      //local
                      "https://github.com/login/oauth/authorize?client_id=5bb2c0fab941fb5b8f9f&scope=repo:status read:repo_hook user:email&redirect_uri=http://localhost:3000/user/github/callback";

                    // 최종 주소

                    // "https://github.com/login/oauth/authorize?client_id=5bb2c0fab941fb5b8f9f&scope=repo:status read:repo_hook user:email&redirect_uri=http://scopewith.com/user/github/callback";
                  }}
                >
                  깃허브로그인
                </GithubBtn>
                <KakaoBtn
                  onClick={() => {
                    setShowModal(true);
                    window.location.href =
                      //s3
                      // "https://kauth.kakao.com/oauth/authorize?client_id=2f892c61e0552c3f50223077e2fc5c6c&redirect_uri=http://kbumsoo.s3-website.ap-northeast-2.amazonaws.com/user/kakao/callback&response_type=code";

                      //local
                      "https://kauth.kakao.com/oauth/authorize?client_id=2f892c61e0552c3f50223077e2fc5c6c&redirect_uri=http://localhost:3000/user/kakao/callback&response_type=code";

                    // 최종 주소
                    // "https://kauth.kakao.com/oauth/authorize?client_id=2f892c61e0552c3f50223077e2fc5c6c&redirect_uri=http://scopewith.com/user/kakao/callback&response_type=code";
                  }}
                >
                  카카오로그인
                </KakaoBtn>
              </Grid>
            </Grid>
          </Grid>
          <Grid display="flex" justifyContent="center" margin="10px 0 30px 0">
            <Grid width="20%" backgroundColor="#B29CF4" height="3px"></Grid>
          </Grid>
        </ModalWrap>
      </Dialog>
    );
  }
};

const ModalWrap = styled.div`
  width: 550px;
  height: 100%;
  @media (max-width: 570px) {
    width: 82vw;
  }
`;

const GithubBtn = styled.div`
  display: inline-block;
  width: 282px;
  height: 50px;
  margin: 5px auto;
  padding-top: 12px;
  border: 0.5px solid #707070;
  box-sizing: border-box;
  border-radius: 25px;
  font-size: 14px;
  text-align: center;
  color: #555555;
  cursor: pointer;
`;

const KakaoBtn = styled.div`
  display: inline-block;
  width: 282px;
  height: 50px;
  margin: 5px auto;
  padding-top: 12px;
  border: 0.5px solid #707070;
  box-sizing: border-box;
  border-radius: 25px;
  font-size: 14px;
  text-align: center;
  color: #606060;
  cursor: pointer;
  background-color: #f9e000;
`;

export default LoginModal;
