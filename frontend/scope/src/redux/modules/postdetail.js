import { createAction, handleActions } from "redux-actions";
import { produce } from "immer";
import { apis } from "../../lib/axios";
import Swal from "sweetalert2";
// 수정
const EDIT_POST = "EDIT_POST";
// 삭제
const DELETE_POST = "DELETE_POST";
// 상태 체크
const STATUS_POST = "STATUS_POST";

// 포스트 수정 액션생성함수 생성
const editPost = createAction(EDIT_POST, (editcard) => ({ editcard }));
// 포스트 삭제 액션생성함수 생성
const deletePost = createAction(DELETE_POST, (postId) => ({ postId }));
// 포스트 상태체크 액션생성함수 생성
const statusPost = createAction(STATUS_POST, (postId) => ({ postId }));

// 초기값
const initialState = {
  list: [],
};

// 포스트 수정
export const editPostAPI = (post_id, editcard) => {
  return function (dispatch, getState, { history }) {
    apis
      .editPost(post_id, editcard)
      .then((res) => {
        history.goBack();
      })
      .catch((err) => {
        console.log(err.response);
        window.alert(err.response.data.msg);
      });
  };
};

// 포스트 삭제
export const deletePostAPI = (postId) => {
  return function (dispatch, getState, { history }) {
    apis
      .deletePost(postId)
      .then((res) => {
        history.goBack();
      })
      .catch((err) => {
        console.log(err.response);
      });
  };
};

// 포스트 상태체크
export const statusPostAPI = (post_id, editstatus) => {
  return function (dispatch, getState, { history }) {
    apis
      .statusPost(post_id, editstatus)
      .then((res) => {
        // history.goBack();
      })
      .catch((err) => {
        console.log(err.response);
      });
  };
};

//북마크 전송
const bookMarkAPI = (postId) => {
  return function (dispatch, getState, { history }) {
    apis
      .bookMarkChecked(postId)
      .then((res) => {
        console.log(res);
        console.log(res.data.data.isBookmarkChecked);
        if (res.data.msg == "북마크 추가 성공") {
          return Swal.fire("관심 프로젝트에 추가되었습니다!", "", "success");
        }
        if (res.data.msg == "북마크 삭제 성공") {
          return Swal.fire("관심 프로젝트 에서 삭제되었습니다!", "", "success");
        }
      })
      .catch((err) => {
        console.log(err.response.data.msg);
        if (err.response.data.msg == "해당 유저를 찾을 수 없습니다.") {
          return Swal.fire("해당 유저를 찾을 수 없습니다!", "", "question");
        }
        if (err.response.data.msg == "해당 프로젝트를 찾을 수 없습니다.") {
          return Swal.fire("해당 프로젝트를 찾을 수 없습니다!", "", "question");
        }
        if (err.response.data.msg == "자신의 게시물은 북마크할 수 없습니다.") {
          return Swal.fire(
            "자신의 게시물은 북마크 할 수 없습니다!",
            "",
            "warning"
          );
        }
        if (err.response.data.msg == "로그인 사용자만 사용할 수 있습니다.") {
          return Swal.fire(
            "로그인 사용자만 이용할 수 있습니다!",
            "",
            "warning"
          );
        }
        if (err.response.data.msg == "입력 값이 잘못되었습니다.") {
          return Swal.fire(
            "로그인 사용자만 이용할 수 있습니다!",
            "",
            "warning"
          );
        }
      });
  };
};

// 리듀서
export default handleActions(
  {
    [EDIT_POST]: (state, action) =>
      produce(state, (draft) => {
        console.log("포스트 수정", action.payload);
      }),
    [DELETE_POST]: (state, action) =>
      produce(state, (draft) => {
        console.log("포스트 삭제", action.payload);
      }),
    [STATUS_POST]: (state, action) =>
      produce(state, (draft) => {
        console.log("포스트 상태 체크", action.payload);
      }),
  },
  initialState
);

const postDetailActions = {
  editPostAPI,
  deletePostAPI,
  statusPostAPI,
  bookMarkAPI,
};

export { postDetailActions };
