import { createBrowserHistory } from "history";
import thunk from "redux-thunk";
import { createStore, combineReducers, applyMiddleware, compose } from "redux";
import { connectRouter } from "connected-react-router";
import User from "./modules/user";

import Post from "./modules/post";
import Stack from "./modules/stack";
import Card from "./modules/postadd";
import Sort from "./modules/sort";
import ReBook from "./modules/bookRecommend";
import Infinitys from "./modules/infinity";
import ApplyUser from "./modules/applyProject";
import Mypage from "./modules/myPage";

export const history = createBrowserHistory();

const rootReducer = combineReducers({
  user: User,
  //   product: Product,
  //   comment : Comment,
  apply: ApplyUser,
  post: Post,
  stack: Stack,
  card: Card,
  sort: Sort,
  rebook: ReBook,
  infinity: Infinitys,
  mypage: Mypage,

  // 8. 리덕스에 history를 이제 넣어줄 것이다. 우리가 만든 history와 우리의 라우터가 연결이되는 것이다. 그리고 이것의 우리의 스토어에 저장이되는 것이다.
  router: connectRouter(history),
});

const middlewares = [thunk.withExtraArgument({ history: history })];

const env = process.env.NODE_ENV;

if (env === "development") {
  const { logger } = require("redux-logger");
  middlewares.push(logger);
}

// 4. 리덕스 데브툴(redux devTools 설정)
const composeEnhancers =
  // 브라우저일 때만  window === "object"이 부분을 돌려주라고 넣어준것이다. __REDUX_DEVTOOLS_EXTENSION_COMPOSE__부분을 데브툴이 있으면 열어주려고 하는 것이다.
  typeof window === "object" && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
        // Specify extension’s options like name, actionsBlacklist, actionsCreators, serialize...
      })
    : compose;

// 5. 미들웨어 묶어주기
// composeEnhancers를 사용해서 applyMiddleware로 지금까지 있었던 미들웨어를 사용한다는 말이다.
const enhancer = composeEnhancers(applyMiddleware(...middlewares));

let store = (initialStore) => createStore(rootReducer, enhancer);

export default store();
