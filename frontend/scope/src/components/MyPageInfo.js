/* eslint-disable */

// 나의 마이페이지에서 뜨는 버튼들과 다른사람 마이페이지에서 뜨는 버튼들
// import를 한다.
import React from "react";
import { Grid, Image, Text, Button } from "../elements/Index";
import { postActions } from "../redux/modules/post";
import { useSelector, useDispatch } from "react-redux";
import styled from "styled-components";
import { apis } from "../lib/axios";
import MypagePostList from "./mypagePost/MypagePostList";
import MarkdownRead from "./MarkdownRead";
import { history } from "../redux/configureStore";
import Spinner from "../shared/Spinner";
import Banners from "./myPage/Banners";
import MypageCard from "./myPage/MypageCard";
import TypeResultTest from "./myPage/TypeResultTest";
import MypageFilter from "./myPage/MypageFilter";

// MyPageInfo의 함수형 컴포넌트를 만든다.
const MyPageInfo = (props) => {
  const dispatch = useDispatch();

  const userId = props.match.params.id;
  const myUserId = useSelector((state) => state.user.userId);

  const [filter, setFilter] = React.useState("소개");
  const [mydata, setMydata] = React.useState();
  const [editMyProfile, setEditMyProfile] = React.useState(false); // 내려줘야함
  const [techStack, setTeckstack] = React.useState([]); //
  const [nickName, setNickName] = React.useState(); //
  const [email, setEmail] = React.useState(); //
  const myType = mydata?.user.userPropensityType;
  const [modal, setModal] = React.useState(false);
  const [testmodal, setTestModal] = React.useState(false);

  //click

  const [loading, setLoading] = React.useState(true);

  const SetFilter = (data) => {
    setFilter(data);
  };

  React.useEffect(() => {
    // dispatch(myPageActions.getMypageAPI(userId));
    console.log(editMyProfile);
    setEmail(null);
    setNickName(null);

    dispatch(postActions.isMainPage(false));
    dispatch(postActions.whatPage("myPage"));
    const fetchData = async () => {
      try {
        const result = await apis.getMypage(userId);

        // setMydata(result.data.data);
        setNickName(result.data.data.user.nickname);
        setEmail(result.data.data.user.email);
        setTeckstack(result.data.data.user.techStackList);
        setLoading(false);
        // console.log(result);
      } catch (err) {
        console.log(err);
      }
    };
    fetchData();
    console.log(mydata);
  }, [editMyProfile]);

  React.useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await apis.getMypage(userId);
        console.log(result);
        setMydata(result.data.data);

        setLoading(false);
      } catch (err) {
        console.log(err);
      }
    };
    fetchData();
    console.log(mydata);
  }, [filter]);

  const introduction = mydata?.user.introduction ? true : false;
  const recruitmentProject = mydata?.recruitment;
  const inProgressProject = mydata?.inProgress;
  const bookMarkProject = mydata?.bookmark;
  const endProject = mydata?.end;

  const EmailConfirm = () => {
    setModal(true);
  };

  const EditTest = () => {
    setTestModal(true);
  };

  const TestClose = () => {
    setTestModal(false);
  };

  const EditProfile = () => {
    setEditMyProfile(true);
  };

  const editProfileCancle = () => {
    setEditMyProfile(false);
  };

  return (
    <Grid margin="0 0 250px 0">
      {loading ? (
        <Spinner />
      ) : (
        <>
          {mydata && myType && (
            <>
              <Banner>
                <Banners
                  type={myType}
                  myPage={mydata?.isMyMypage}
                  // setModal={setModal}
                  // modal={modal}
                  // onClick={() => {
                  //   EmailConfirm();
                  // }}
                ></Banners>
              </Banner>
              <MypageCard
                setEditMyProfile={setEditMyProfile}
                editMyProfile={editMyProfile}
                mydata={mydata}
                myType={myType}
                myPage={mydata?.isMyMypage}
                myUserId={myUserId}
                userId={userId}
                nickName={nickName}
                email={email}
                techStack={techStack}
                onClick={EditProfile}
                onClick2={editProfileCancle}
              />
              <TypeResultTest
                myType={myType}
                userId={userId}
                myUserId={myUserId}
                mydata={mydata}
                testmodal={testmodal}
                EditTest={EditTest}
                TestClose={TestClose}
                setModal={setModal}
                modal={modal}
                onClick={() => {
                  EmailConfirm();
                }}
              />
              <Grid
                display="flex"
                margin="auto"
                justifyContent="center"
                margin="0px 0 0 150px"
                width="auto"
              >
                <MypageFilter filter={filter} onClicks={SetFilter} />
              </Grid>

              {filter === "모집" && (
                <MypagePostList {...recruitmentProject}></MypagePostList>
              )}

              <Grid margin="0 0 0 34%" width="49%">
                {filter === "모집" && recruitmentProject.length === 0 && (
                  <>
                    <NoIntroduction src="/img/소개글너구리.png"></NoIntroduction>
                    <NoIntroductionText>
                      프로젝트가 아직 없네요.
                    </NoIntroductionText>
                  </>
                )}
              </Grid>

              {filter === "진행중" && (
                <MypagePostList {...inProgressProject}></MypagePostList>
              )}
              <Grid margin="0 0 0 34%" width="49%">
                {filter === "진행중" && inProgressProject.length === 0 && (
                  <>
                    <NoIntroduction src="/img/소개글너구리.png"></NoIntroduction>
                    <NoIntroductionText>
                      프로젝트가 아직 없네요.
                    </NoIntroductionText>
                  </>
                )}
              </Grid>
              {filter === "관심" && (
                <MypagePostList {...bookMarkProject}></MypagePostList>
              )}
              <Grid margin="0 0 0 34%" width="49%">
                {filter === "관심" && bookMarkProject.length === 0 && (
                  <>
                    <NoIntroduction src="/img/소개글너구리.png"></NoIntroduction>
                    <NoIntroductionText>
                      프로젝트가 아직 없네요.
                    </NoIntroductionText>
                  </>
                )}
              </Grid>
              {filter === "완료" && (
                <MypagePostList {...endProject}></MypagePostList>
              )}
              <Grid margin="0 0 0 34%" width="49%">
                {filter === "완료" && endProject.length === 0 && (
                  <>
                    <NoIntroduction src="/img/소개글너구리.png"></NoIntroduction>
                    <NoIntroductionText>
                      프로젝트가 아직 없네요.
                    </NoIntroductionText>
                  </>
                )}
              </Grid>
              {filter === "소개" &&
                mydata?.isMyMypage === true &&
                introduction === true && (
                  <button
                    style={{
                      float: "right",
                      margin: "55px 18% 0 0",
                      border: "none",
                      borderRadius: "15px",
                      cursor: "pointer",
                      backgroundColor: " transparent ",
                    }}
                    onClick={() => {
                      history.push({
                        pathname: "/addmarkdown",
                        state: { userId: userId },
                      });
                    }}
                  >
                    <img
                      src="/img/소개글.png"
                      style={{ backgroundColor: "rgba(0, 0, 0, 0)" }}
                    />
                  </button>
                )}
              <Grid margin="0 0 0 34%" width="49%">
                {filter === "소개" && introduction === true && (
                  <Grid margin="50px 0 0 0" border="1px solid #707070 ">
                    <MarkdownRead
                      introduction={mydata?.user.introduction}
                    ></MarkdownRead>
                  </Grid>
                )}
              </Grid>
              <Grid margin="0 0 0 34%" width="49%">
                {filter === "소개" && introduction === false && (
                  <>
                    <NoIntroduction src="/img/소개글너구리.png"></NoIntroduction>
                    <NoIntroductionText>
                      작성된 소개가 없네요.
                    </NoIntroductionText>
                    {mydata?.isMyMypage === true && (
                      <Button
                        width="150px"
                        margin="0px 0 0 45%"
                        _onClick={() => {
                          history.push({
                            pathname: "/addmarkdown",
                            state: { userId: userId },
                          });
                        }}
                      >
                        {" "}
                        소개글 작성하기
                      </Button>
                    )}
                  </>
                )}
              </Grid>

              {/* 소개글 있거나 없거나 */}
            </>
          )}
          {/* //  mydata와 myType가 있을때 */}
        </>
        // 스피너를 감싸는 친구
      )}
      {/* 스피너를 감싸는 괄호 */}
    </Grid>
  );
};

const Banner = styled.div`
  width: 100%;
  margin: -100px auto;
  display: flex;
  height: 457px;
  overflow: hidden;
`;
const NoIntroduction = styled.img`
  width: 50%;
  height: 50%;
  object-fit: cover;
  position: relative;
  margin: auto;
  display: flex;
  justify-content: center;
`;
const NoIntroductionText = styled.p`
  color: #737373;
  font-size: 25px;
  width: auto;
  align-items: center;
  display: flex;
  justify-content: center;
  margin-left: 60px;
`;
export default MyPageInfo;
