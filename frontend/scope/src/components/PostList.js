/* eslint-disable */
import React from "react";
import { useSelector, useDispatch } from "react-redux";
import Post from "./Post";
import PostTest from "./PostTest";
import { postActions } from "../redux/modules/post";
import styled from "styled-components";
// import { useInView } from "react-intersection-observer";
import { height } from "@mui/system";
import { Grid, Image, Text, Button } from "../elements/Index";

const PostList = (props) => {
  const dispatch = useDispatch();
  let stack = useSelector((state) => state.stack.stack);
  let sort = useSelector((state) => state.sort.sort);
  // let paging = useSelector((state) => state?.infinity.paging.next);
  let paging = props.paging;
  let reBook = useSelector((state) => state.rebook.reBook);
  let post_list = useSelector((state) => state.post.posts);
  // console.log(props);
  // let post_list = props.post.data.data;
  let posts = post_list.slice(0, paging);

  return (
    <React.Fragment>
      <PostWrap>
        {posts && (
          <>
            {posts.map((item, index) => {
              return <Post key={index} {...item} />;
            })}
          </>
        )}
        {posts.length === 0 && (
          <Grid margin="auto" width="100%" height="100%" display="flex">
            <NoIntroduction src="/img/소개글너구리.png"></NoIntroduction>
            <NoIntroductionText>
              아직 포스트가 없네요
              <br /> .
              <br /> .
              <br /> .
            </NoIntroductionText>
          </Grid>
        )}
      </PostWrap>
    </React.Fragment>
  );
};

const PostWrap = styled.div`
  max-width: 100%;
  margin: auto;
  display: grid;

  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  /* grid-gap: 1px; */
  @media (max-width: 1700px) {
    width: 100%;
  }
`;
const NoIntroduction = styled.img`
  width: 100%;
  height: 100%;
  /* object-fit: cover; */

  margin: auto;
  display: flex;
  justify-content: center;
`;

const NoIntroductionText = styled.p`
  color: #737373;
  font-size: 25px;
  width: auto;
  align-items: center;
  display: flex;
  justify-content: center;
  margin-left: auto;
`;

export default PostList;
