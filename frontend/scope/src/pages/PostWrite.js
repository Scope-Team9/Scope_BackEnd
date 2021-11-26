// PostWrite.js
/* eslint-disable */

// import를 한다.
import React from "react";
import AddPost from "../components/AddPost";
import { postActions } from "../redux/modules/post";
import { useDispatch } from "react-redux";

// PostWrite의 함수형 컴포넌트를 만든다.
const PostWrite = (props) => {
  const dispatch = useDispatch();

  React.useEffect(() => {
    dispatch(postActions.isMainPage(false));
  }, []);

  return (
    <React.Fragment>
      <AddPost />
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default PostWrite;
