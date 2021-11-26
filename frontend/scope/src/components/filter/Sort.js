/* eslint-disable */
import React from "react";
import SortText from "./SortText";
import { Grid, Button } from "../../elements/Index";
import styled from "styled-components";
import { postActions } from "../../redux/modules/post";
import { sortAction } from "../../redux/modules/sort";
import { bookRecommendAction } from "../../redux/modules/bookRecommend";
import { pageAction } from "../../redux/modules/infinity";
import { useSelector, useDispatch } from "react-redux";
import Swal from "sweetalert2";

const Sort = (props) => {
  const dispatch = useDispatch();
  const isLoginUser = useSelector((state) => state.user.userId);
  const [arr, setArr] = React.useState([
    {
      id: "최신",
      status: "createdAt",
      active: false,
    },
    {
      id: "마감순",
      status: "deadline",
      active: false,
    },
    {
      id: "북마크",
      status: "bookmark",
      active: false,
    },
    {
      id: "추천",
      status: "recommend",
      active: false,
    },
  ]);

  const onclickSort = (data) => {
    console.log("최신 마감순", data);
    dispatch(postActions.isMainPage(true));
    dispatch(sortAction.getSort(data));
    dispatch(bookRecommendAction.getRb(""));
    props.setPaging(12);
  };
  //bookmark,recommend
  const onclickRb = (data) => {
    console.log("북마크 추천", data);
    dispatch(postActions.isMainPage(true));
    dispatch(bookRecommendAction.getRb(data));
    dispatch(sortAction.getSort(""));
    // if (paging > 0) {
    //   setPaging(paging - 1);
    // }
    props.setPaging(12);
  };

  const Filter = (getItem) => {
    setArr((state) => {
      return state.map((stateItem) => {
        if (stateItem.id === getItem.id) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });

    setArr((state) => {
      return state.map((stateItem) => {
        if (stateItem.id !== getItem.id && stateItem.active === true) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });
  };

  return (
    <>
      {arr && (
        <FilterBox>
          {arr.map((item) => {
            return (
              <SortText
                item={item}
                key={item.id}
                page={props.page}
                onClick={() => {
                  Filter(item);
                  if (item.id === "최신" || item.id === "마감순") {
                    onclickSort(item.status);
                  }
                  if (isLoginUser) {
                    onclickRb(item.status);
                  } else {
                    Swal.fire(
                      "로그인 후 이용하실 수 있습니다!",
                      "로그인하고 프로젝트를 추천받아 보세요!",
                      "warning"
                    );
                  }
                }}
              ></SortText>
            );
          })}
        </FilterBox>
      )}
    </>
  );
};

const FilterBox = styled.div`
  display: flex;
  font-size: 20px;
  margin: 10px auto;
  justify-content: flex-end;
  width: 75%;
  max-width: 1920px;
  @media screen and (max-width: 1850px) {
    justify-content: center;
  }
  @media screen and (max-width: 750px) {
    justify-content: center;
    font-size: 12px;
  }
`;
export default Sort;
