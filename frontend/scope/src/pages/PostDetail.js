// PostDetail.js
// import를 한다.
import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { apis } from "../lib/axios";
import { postActions } from "../redux/modules/post";
import { Grid, Button } from "../elements/Index";
import Swal from "sweetalert2";
import ApplyStatusModal from "../components/ApplyStatusModal";
import LeftBanner from "../components/postDetail/leftBanner";
import TitleDetail from "../components/postDetail/rightContents/TitleDetail";
import ExileUserModal from "../components/modal/ExileUserModal";
import ApplicantDetail from "../components/postDetail/rightContents/ApplicantDetail";
import StackDetail from "../components/postDetail/rightContents/StackDetail";
import DateDetail from "../components/postDetail/rightContents/DateDetail";
import StatusDetail from "../components/postDetail/rightContents/StatusDetail";
import ContentDetail from "../components/postDetail/rightContents/ContentDetail";
import BookMark from "../components/postDetail/rightContents/BookMark";
import ApplicantButton from "../components/postDetail/rightContents/ApplicantButton";
import PosterButton from "../components/postDetail/rightContents/PosterButton";
import TotalMemberDetail from "../components/postDetail/rightContents/TotalMemberDetail";

// PostDetail의 함수형 컴포넌트를 만든다
const PostDetail = props => {
  const dispatch = useDispatch();
  const [checkPost, setCheckPost] = React.useState();
  const [bookmark, setBookmark] = React.useState(false);
  const [applyStatusModal, setApplyStatusModal] = React.useState(false); //신청현황
  const [applyUserModal, setApplyUserModal] = React.useState(false); //지원취소/팀탈퇴/프로젝트마감
  const [exileStatusModal, setExileStatusModal] = React.useState(false); //강퇴
  const [recruitmentFinish, setRecruitmentFinish] = React.useState(); // 모집완료 체크 for리렌더링
  const [projectStatus, setProjectStatus] = React.useState();
  const [applyValue, setApplyValue] = React.useState();
  const [isme, setIsme] = React.useState(null);

  const applyStatusModalOpen = () => {
    setApplyStatusModal(true);
  };

  const exileStatusModalOpen = () => {
    setExileStatusModal(true);
  };

  const applyUserModalOpen = value => {
    setApplyValue(value);
    setApplyUserModal(true);
  };

  const statusCheck = value => {
    console.log(value);
    setProjectStatus(value);
  };
  // 상태변경
  const edit_status = data => {
    const editstatus = {
      projectStatus: data,
    };
    const statusDoing = async () => {
      try {
        const result = await apis.statusPost(post_id, editstatus);
        setRecruitmentFinish(!recruitmentFinish);
        Swal.fire("모집 완료 되었습니다!", "", "success");
      } catch (err) {
        console.log(err);
      }
    };
    statusDoing();
  };

  let post_id = props.match.params.id;
  const userId = useSelector(state => state.user.userId); //로그인 유저아이디
  const postUserId = checkPost?.data.data.post.userId;
  const passedData = checkPost?.data["data"].post;
  const passedUserStatus = checkPost?.data["data"].userStatus;
  const passdedMenber = checkPost?.data["data"].members;

  React.useEffect(() => {
    dispatch(postActions.isMainPage(false));
    dispatch(postActions.whatPage("postDetail"));
    const CheckPost = async () => {
      try {
        const result = await apis.detailPost(post_id);
        setCheckPost(result);
        console.log(result);
        setIsme(result.data.data.userStatus);
        setProjectStatus(result.data.data.post.projectStatus);
      } catch (err) {
        console.log(err);
      }
    };
    CheckPost();
  }, [
    bookmark,
    applyStatusModal,
    recruitmentFinish,
    exileStatusModal,
    applyUserModal,
    projectStatus,
    applyValue,
  ]);

  //북마크 토글
  const ToggleBookMark = () => {
    const bookMark = async () => {
      try {
        const result = await apis.bookMarkChecked(post_id);
        setBookmark(!bookmark);
      } catch (err) {
        console.log(err);
      }
    };
    bookMark();
  };

  return (
    <React.Fragment>
      <Grid
        display="flex"
        justifyContent="center"
        maxWidth="1920px"
        height="100%"
        border="1px solid #C4C4C4"
        margin="auto"
      >
        <LeftBanner />
        <Grid margin="46px 106px 0px" position="relative">
          <BookMark
            userId={userId}
            postUserId={postUserId}
            ToggleBookMark={ToggleBookMark}
            passedData={passedData}
          />
          <TitleDetail passedData={passedData} />
          <Grid margin="20px auto">
            <Grid display="flex">
              <ApplicantDetail passdedMenber={passdedMenber} />
            </Grid>
            <Grid margin="10px auto ">
              {userId === postUserId && passedData?.projectStatus === "모집중" && (
                <Grid position="relative" width="100%">
                  <Grid
                    position="absolute"
                    right="20px"
                    width="120px"
                    padding="10px"
                  >
                    <ApplyStatusModal
                      applyStatusModal={applyStatusModal}
                      setApplyStatusModal={setApplyStatusModal}
                      postId={post_id}
                    />
                    <ExileUserModal
                      applyStatusModal={exileStatusModal}
                      setApplyStatusModal={setExileStatusModal}
                      postId={post_id}
                    ></ExileUserModal>
                  </Grid>
                </Grid>
              )}

              <Grid display="flex">
                <DateDetail passedData={passedData} />
                <StackDetail passedData={passedData} />
              </Grid>
              <Grid display="flex">
                <TotalMemberDetail passedData={passedData} />

                {passedData?.projectStatus === "모집중" && (
                  <Grid display="flex" width="200px">
                    <Button
                      postion="absolute"
                      common
                      _onClick={applyStatusModalOpen}
                    >
                      신청 현황
                    </Button>
                    <Button
                      postion="absolute"
                      common
                      _onClick={exileStatusModalOpen}
                    >
                      팀원 강퇴
                    </Button>
                  </Grid>
                )}
              </Grid>
              <StatusDetail passedData={passedData} />
              <ContentDetail passedData={passedData} />
              <Grid>
                {userId === postUserId ? (
                  <PosterButton
                    passedData={passedData}
                    applyUserModalOpen={applyUserModalOpen}
                    applyUserModal={applyUserModal}
                    setApplyUserModal={setApplyUserModal}
                    applyValue={applyValue}
                    post_id={post_id}
                    passdedMenber={passdedMenber}
                    edit_status={edit_status}
                    statusCheck={statusCheck}
                  />
                ) : (
                  <Grid textAlign="center">
                    <ApplicantButton
                      passedData={passedData}
                      isme={isme}
                      applyUserModalOpen={applyUserModalOpen}
                      applyUserModal={applyUserModal}
                      setApplyUserModal={setApplyUserModal}
                      applyValue={applyValue}
                      post_id={post_id}
                      passdedMenber={passdedMenber}
                      passedUserStatus={passedUserStatus}
                    />
                  </Grid>
                )}
              </Grid>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default PostDetail;
