// ApplicantButton.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid, Button } from "../../../elements/Index";
import ApplyUserModal from "../../ApplyUserModal";

// ApplicantButton의 함수형 컴포넌트를 만든다.
const ApplicantButton = (props) => {
  return (
    <React.Fragment>
      {props.passedData?.projectStatus === "모집중" && (
        <Grid>
          {props.isme === "user" && (
            <>
              <Button
                common
                width="120px"
                isValue="apply"
                _onClick={(e) => {
                  console.log(e);
                  props.applyUserModalOpen(e.target.value);
                }}
                margin="auto 10px"
                border="1px solid #554475"
                borderRadius="50px"
              >
                지원신청
              </Button>
            </>
          )}

          {props.isme === "applicant" && (
            <Button
              common
              width="120px"
              isValue="cancel"
              _onClick={(e) => {
                props.applyUserModalOpen(e.target.value);
              }}
              width="120px"
            >
              지원취소
            </Button>
          )}

          {props.isme === "member" && (
            <Button
              common
              width="120px"
              isValue="teamExit"
              _onClick={(e) => {
                props.applyUserModalOpen(e.target.value);
              }}
            >
              팀탈퇴
            </Button>
          )}
        </Grid>
      )}

      <ApplyUserModal
        applyUserModal={props.applyUserModal}
        setApplyUserModal={props.setApplyUserModal}
        applyValue={props.applyValue}
        postId={props.post_id}
        passdedMenber={props.passdedMenber}
      />
      {props.passedData?.projectStatus === "종료" &&
        props.passedUserStatus === "member" && (
          <Grid>
            <Button
              common
              width="120px"
              isValue="memberLiked"
              _onClick={(e) => {
                console.log(e);
                props.applyUserModalOpen(e.target.value);
              }}
              margin="auto 10px"
              border="1px solid #554475"
              borderRadius="50px"
            >
              지원신청
            </Button>
          </Grid>
        )}
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default ApplicantButton;
