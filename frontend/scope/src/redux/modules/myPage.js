import { createAction, handleActions } from "redux-actions";
import { produce } from "immer";
import { apis } from "../../lib/axios";

const GET_MYPAGE = "GET_MYPAGE";

const getMypages = createAction(GET_MYPAGE, (data) => ({ data }));

const initialState = {};

export const getMypageAPI = (postId) => {
  return function (dispatch, getState, { history }) {
    apis
      .getMypage(postId)
      .then((res) => {
        console.log(res);
        // dispatch(getPosts(data));
      })
      .catch((err) => {
        console.log(err.response);
      });
  };
};

export default handleActions(
  {
    [GET_MYPAGE]: (state, action) =>
      produce(state, (draft) => {
        console.log("여기가 마이페이지인가", action.payload);
      }),
  },
  initialState
);

const myPageActions = { getMypageAPI };
export { myPageActions };
