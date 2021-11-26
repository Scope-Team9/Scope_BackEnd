/* eslint-disable */
import { createAction, handleActions } from "redux-actions";
import { produce } from "immer";
import { apis } from "../../lib/axios";
import { postActions } from "./post";
import { useSelector, useDispatch } from "react-redux";

const GET_PAGE = "GET_PAGE";

const getPages = createAction(GET_PAGE, (data) => ({ data }));

const initialState = {
  paging: { start: 12, next: 12 },
};

export const getPage = (data) => {
  return function (dispatch, getState, { history }) {
    let mainPage = getState().post.mainpage;
    let whatPages = getState().post.whatPage;
    console.log(data);

    if (mainPage === false && whatPages.now !== "mainPage") {
      // console.log("메인페이지 무한스크롤 펄스값", mainPage);
      return;
    } else {
      // console.log("메인페이지 무한스크롤 트루값", mainPage);
      dispatch(getPages(data));
    }
  };
};

export default handleActions(
  {
    [GET_PAGE]: (state, action) =>
      produce(state, (draft) => {
        // console.log("액션값을 받아야함", action);
        // console.log(state.paging);

        let paging = {
          start: state.paging.next,
          next: action.payload.data,
        };

        // console.log("액션값을 받아야함2222", paging);
        draft.paging = paging;
      }),
  },
  initialState
);

const pageAction = {
  getPage,
};
export { pageAction };
