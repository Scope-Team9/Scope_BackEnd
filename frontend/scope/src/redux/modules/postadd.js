import { createAction, handleActions } from "redux-actions";
import { produce } from "immer";
import { apis } from "../../lib/axios";

// Actions
const ADD_POST = "ADD_POST";

// Action Creators
const addPosts = createAction(ADD_POST, (card) => ({ card }));

const initialState = {
  list: [
    {
      title: "제목",
      summary: "한줄설명",
      contents: "내용",
      techStackList: "기술스택",
      totalMember: "제한 인원",
      projectstatus: "프로젝트 상태",
      startdate: "시작 날짜",
      enddate: "끝나는 날짜",
    },
  ],
};

// 미들웨어
export const addPostAPI = (card) => {
  return function (dispatch, getState, { history }) {
    apis
      .addPost(card)
      .then((res) => {
        console.log(res);
        history.goBack();
      })
      .catch((err) => {
        console.log(err.response);
      });
    dispatch(addPosts(card));
  };
};

// 리듀서
export default handleActions(
  {
    [ADD_POST]: (state, action) => produce(state, (draft) => {}),
  },
  initialState
);

const postAddActions = {
  addPostAPI,
};

export { postAddActions };
