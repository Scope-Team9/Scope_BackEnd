/* eslint-disable */
import React, { useEffect, useCallback } from "react";
import _ from "lodash";
import { postActions } from "../redux/modules/post";
import { useSelector, useDispatch } from "react-redux";

const Infinity = (props) => {
  const { children, callNext, paging, isLoading, isNext } = props;
  const isMainPage = useSelector((state) => state.post.mainpage);
  // console.log("이거 확인좀 해봐야겠네", props);

  const _handleScroll = _.throttle(() => {
    const { innerHeight } = window;
    const { scrollHeight } = document.body;
    const scrollTop =
      (document.documentElement && document.documentElement.scrollTop) ||
      document.body.scrollTop;
    //1. 페이지 5페이지 이상으로 넘어감.
    if (scrollHeight - innerHeight - scrollTop < 300) {
      callNext();
    }
  }, 600);

  const handleScroll = useCallback(_handleScroll, []);
  // 메인페이지에서 Hook으로 커스텀 HOOK
  useEffect(() => {
    if (isLoading) {
      return;
    }

    if (isMainPage === true) {
      window.addEventListener("scroll", () => {
        handleScroll();
      });
    } else {
      console.log("여기서 막힘?");
      window.removeEventListener("scroll", handleScroll);
    }
    return () => window.removeEventListener("scroll", handleScroll);
  }, [paging, isMainPage]);
  return <div>{children}</div>;
};

Infinity.defaultProps = {
  chidlren: null,
  callNext: () => {},
  isLoading: false,
};

export default Infinity;
