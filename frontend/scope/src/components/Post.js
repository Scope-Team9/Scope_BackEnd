/* eslint-disable */
import React from "react";
import styled from "styled-components";
import PostStacks from "./PostStacks";
import ApplyUserModal from "./ApplyUserModal";
import { useSelector, useDispatch } from "react-redux";
import { applyCreators } from "../redux/modules/applyProject";
import { apis } from "../lib/axios";
import { history } from "../redux/configureStore";
import { Grid, Image, Text, Button } from "../elements/Index";

// Post의 함수형 컴포넌트를 만든다.
const Post = props => {
  const dispatch = useDispatch();
  const myPage = useSelector(state => state.post.whatPage.now);
  const myUserId = useSelector(state => state.user.userId);
  const [stacks, setStacks] = React.useState();
  const [applyUserModal, setApplyUserModal] = React.useState(false); //지원취소/팀탈퇴/프로젝트마감
  const [applyValue, setApplyValue] = React.useState();
  const [member, setMember] = React.useState();

  let totalmember = props.totalMember;
  let recruitmentMember = props.recruitmentMember;

  const modalOpen = (value, postId) => {
    setApplyValue(value);
    setApplyUserModal(true);
  };

  // React.useEffect(() => {
  //   let postId = props.postId;
  //   dispatch(applyCreators.getMemberAPI(postId));
  // }, [props.mypage]);

  React.useEffect(() => {
    if (myPage !== "myPage") {
      return;
    }
    let postId = props.postId;
    const getMembers = async () => {
      try {
        const result = await apis.getMember(postId);
        console.log("호출되나", result);
        setMember(result.data.data);
      } catch (err) {
        console.log(err);
      }
    };
    getMembers();
  }, []);

  // console.log(member);
  let as = member?.find(e => e.userId === myUserId);
  console.log(as);

  return (
    <React.Fragment>
      <ProductImgWrap
        onClick={() => {
          history.push({
            pathname: `/postdetail/${props.postId}`,
          });
        }}
      >
        {member &&
          props.mypage &&
          props.projectStatus === "종료" &&
          member[0].assessment === true &&
          as.assessment === false && (
            <Grid
              bg="#111"
              width="100%"
              position="absolute"
              zIndex="11"
              borderRadius="34px"
              opacity="0.8"
              display="flex"
            >
              <Button
                isValue="memberLiked"
                backgroundColor="#fff"
                width="50%"
                color="#111"
                hoverBg="#b29cf4"
                hoverCl="#fff"
                _onClick={e => {
                  e.stopPropagation();
                  console.log(e.target.value, props.postId);
                  modalOpen(e.target.value, props.postId);
                }}
              >
                팀원평가하기
              </Button>
              <ApplyUserModal
                applyUserModal={applyUserModal}
                setApplyUserModal={setApplyUserModal}
                applyValue={applyValue}
                passdedMenber={member}
                postId={props.postId}
                myPage={props.mypage}
              />
            </Grid>
          )}
        {/* 전체크기 */}
        <DDescriptionBox>
          <CardHeader
            projectStatus={props.projectStatus}
            id="headerOne"
            className="headerOne"
          >
            <Grid>{/* <TitleDate>D-2</TitleDate> */}</Grid>
            <Grid
              position="relative"
              zIndex="10"
              display="flex"
              width="80%"
              margin="auto"
            >
              {props.techStack.map((p, idx) => {
                return (
                  <Grid margin="4% 0 0 -1% " width="20%" key={idx}>
                    <PostStacks stack={p}></PostStacks>
                  </Grid>
                );
              })}
            </Grid>
          </CardHeader>

          <DescriptionBox>
            <ProjectState projectStatus={props.projectStatus}>
              {props.projectStatus}
            </ProjectState>

            <Title>{props.title}</Title>
            {/* <Summary>{props.summary}</Summary> */}
            <Date>
              {props.startDate} ~ {props.endDate}
            </Date>

            {/* 프로그래스바 */}
            <Grid
              display="flex"
              width="100%"
              justifyContent="space-between"
              height="20%"
            >
              <ProgressBar projectStatus={props.projectStatus}>
                <HighLight
                  projectStatus={props.projectStatus}
                  width={(recruitmentMember / totalmember) * 100 + "%"}
                />
              </ProgressBar>
              <Grid width="43%" textAlign="right">
                <Text size="15px" margin="0 0 0 10px" bold="500">
                  {recruitmentMember + "/" + totalmember} 명 참여중
                </Text>
              </Grid>
            </Grid>
            {/* 프로그래스바까지 */}
          </DescriptionBox>
        </DDescriptionBox>
      </ProductImgWrap>
    </React.Fragment>
  );
};

const DescriptionBox = styled.div`
  position: relative;
  margin: 25px 20px;
  padding: 10px;
`;
const DDescriptionBox = styled.div`
  /* background-color: #fff5f9; */
  border-radius: 21px;
  margin: auto;
  position: relative;
  height: 100%;
  width: 100%;
`;

//카드 헤더
const CardHeader = styled.div`
  position: relative;
  width: 100%;
  height: 55px;
  border-radius: 21px 21px 0px 0px;
  background-color: #ecc0f1;
  ${props => props.projectStatus === "모집중" && `background-color: #17334A;`};
  ${props => props.projectStatus === "진행중" && `background-color: #17334A;`};
  ${props => props.projectStatus === "종료" && `background-color: #878787;`};
`;

//헤더 까지

const Title = styled.span`
  margin-top: 8%;
  margin-bottom: 14%;
  font-size: 20px;
  width: 100%;
  font-weight: 500;
  /* white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #606060; */
  white-space: normal;
  line-height: 1.2;
  height: 2.4em;
  text-align: left;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
`;

const Date = styled.div`
  display: flex;
  justify-content: flex-start;
  margin: 8% auto 15% auto;
  width: 100%;
  font-size: 14px;
  text-overflow: ellipsis;
  @media (max-width: 750px) {
    font-size: 14px;
    margin-left: 0%;
  }
  @media (max-width: 360px) {
    font-size: 14px;
    margin-left: 0%;
  }
`;

const ProjectState = styled.div`
  color: #fff;
  border-radius: 15px;
  width: 58px;
  justify-content: center;
  align-items: center;
  display: flex;
  height: 24px;
  margin: 4% 0;
  font-size: 13px;
  ${props => props.projectStatus === "모집중" && `background-color: #2699FB;`};
  ${props => props.projectStatus === "진행중" && `background-color: #15B915;`};
  ${props => props.projectStatus === "종료" && `background-color: #878787;`};
`;

const ProductImgWrap = styled.div`
  z-index: 1;
  position: relative;
  background-color: white;
  width: 330px;
  height: 330px;
  max-width: 350px;
  margin: 30px auto;
  border-radius: 20px;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.12), 0 2px 5px rgba(0, 0, 0, 0.24);
  @media (max-width: 1700px) {
    margin: auto;
    margin-top: 30px;
    margin-bottom: 30px;
  }
  @media (max-width: 1300px) {
    margin: auto;
    margin-top: 30px;
    margin-bottom: 30px;
  }
  @media (max-width: 450px) {
    margin: auto;
    margin-top: 30px;
    margin-bottom: 30px;
  }
`;
//프로그래스바
const ProgressBar = styled.div`
  border: 1px solid #111;
  background: #f6f4f6;
  width: 55%;
  height: 15px;
  border: none;
  ${props => props.projectStatus === "모집중" && `background-color: #BCE0FD;`};
  ${props => props.projectStatus === "진행중" && `background-color: #DFDFDF;`};
  ${props => props.projectStatus === "종료" && `background-color: #DFDFDF;`};
`;

const HighLight = styled.div`
  transition: 1s;
  width: ${props => props.width};
  ${props => props.projectStatus === "모집중" && `background-color: #2699FB;`};
  ${props => props.projectStatus === "진행중" && `background-color: #878787 ;`};
  ${props => props.projectStatus === "종료" && `background-color: #878787;`};
  height: 15px;
`;

//프로그래스바 까지

export default Post;
