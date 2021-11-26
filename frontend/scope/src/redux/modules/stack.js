import { createAction, handleActions } from "redux-actions";
import { produce } from "immer";

const GET_STACK = "GET_STACK";
const SET_STACK = "SET_STACK";

const getStack = createAction(GET_STACK, (data) => ({ data }));
const setStack = createAction(SET_STACK, (data) => ({ data }));

const initialState = {
  stack: {
    React: "",
    Java: "",
    JavaScript: "",
    Python: "",
    Node: "",
    cpp: "",
    Flask: "",
    Django: "",
    Vue: "",
    Spring: "",
    php: "",
    Swift: "",
    Kotlin: "",
    TypeScript: "",
  },
};

export default handleActions(
  {
    [GET_STACK]: (state, action) =>
      produce(state, (draft) => {
        const st = action.payload.data;
        // if로 분기를 둔 이유 : draft에 이미 값이 있을 때와 없을 때를 분리 (처음에는 draft에 값이 없을 것 이므로 따로 작성)
        if (!draft) {
          const something = state.stack;
          //   console.log(something[st]);
          something[st] = st;
          //   console.log(something);
          draft.stack = something;
          //   console.log(draft.stack);
        } else {
          draft.stack[st] = st;
          //   console.log(draft.stack);
        }

        // const something = state.stack;
        // console.log(something[st]);
        // something[st] = st;
        // console.log(something);
        // draft = something;

        // 처음에는 기본값인 state를 변경해줬었는데 이는 위험해 보임. => 처음에 변수에 이 값을 저장
        // console.log(state.stack[st]);
        // state.stack[st] = st;
        // console.log(state);

        // draft.push(action.payload.data);
        // console.log("이것이 드래프트", draft.list);
      }),
    [SET_STACK]: (state, action) =>
      produce(state, (draft) => {
        // console.log(action.payload.data);
        const st = action.payload.data;
        draft.stack[st] = "";
        // draft.splice(draft.findIndex((item) => item === action.payload.data));
        // const result = draft.filter((item) => {
        //   if (item !== action.payload.data) {
        //     return item;
        //   }
        // });
        // console.log("빼고 검색한 값", result);
        // draft = result;
        // console.log("이것이 드래프트", draft);
      }),
  },
  initialState
);

const stackAction = {
  getStack,
  setStack,
};
export { stackAction };
