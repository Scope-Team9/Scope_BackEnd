import { createAction, handleActions } from "redux-actions";
import { produce } from "immer";

const GET_SORT = "GET_SORT";

const getSort = createAction(GET_SORT, (data) => ({ data }));

const initialState = {
  sort: "createdAt",
};

export default handleActions(
  {
    [GET_SORT]: (state, action) =>
      produce(state, (draft) => {
        draft.sort = action.payload.data;
      }),
  },
  initialState
);

const sortAction = {
  getSort,
};
export { sortAction };
