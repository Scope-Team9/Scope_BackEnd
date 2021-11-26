/* eslint-disable */
import React from "react";
import styled from "styled-components";
import { Grid, Image, Text, Button } from "../../elements/Index";
import MyFilter from "./filter/MyFilter";

const MypageFilter = (props) => {
  console.log(props);
  const [arr, setArr] = React.useState([
    {
      id: "소개",
      active: true,
    },
    {
      id: "진행중",
      active: false,
    },
    {
      id: "관심",
      active: false,
    },
    {
      id: "완료",
      active: false,
    },
    {
      id: "모집",
      active: false,
    },
  ]);
  const filters = (item) => {
    setArr((state) => {
      return state.map((stateItem) => {
        if (stateItem.id === item.id) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });
    setArr((state) => {
      return state.map((stateItem) => {
        if (stateItem.id !== item.id && stateItem.active === true) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });
  };

  return (
    <>
      {arr && (
        <Grid display="flex" margin=" 0 0 0 40%">
          {arr.map((item) => {
            return (
              <MyFilter
                onClick={() => {
                  if (item.id !== props.filter) {
                    filters(item);
                  }
                }}
                key={item.id}
                {...item}
                setStatus={props.onClicks}
              ></MyFilter>
            );
          })}
        </Grid>
      )}
    </>
  );
};

export default MypageFilter;
