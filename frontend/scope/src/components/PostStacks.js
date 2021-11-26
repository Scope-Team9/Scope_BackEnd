/* eslint-disable */
import React from "react";
import styled from "styled-components";
import { useSelector, useDispatch } from "react-redux";

import { history } from "../redux/configureStore";
import { Grid, Image, Progress } from "../elements/Index";
import Img from "../images/flutter.png";

const PostStacks = props => {
  const whatPage = useSelector(state => state.post.whatPage);
  const reduxstack = useSelector(state => state.stack.stack);
  const [stacks, setStacks] = React.useState(9);
  const [Stack, setStack] = React.useState([
    {
      id: "React",
      img: "/img/react.png",
      active: false,
    },
    {
      id: "Java",
      img: "/img/java.png",
      active: false,
    },
    {
      id: "JavaScript",
      img: "/img/javascript.png",
      active: false,
    },
    {
      id: "Python",
      img: "/img/python.png",
      active: false,
    },
    {
      id: "Node",
      img: "/img/node.js.png",
      active: false,
    },
    {
      id: "cpp",
      img: "/img/c__.png",
      active: false,
    },
    {
      id: "Flask",
      img: "/img/flask.png",
      active: false,
    },
    {
      id: "Django",
      img: "/img/django.png",
      active: false,
    },
    {
      id: "Vue",
      img: "/img/vue.png",
      active: false,
    },
    {
      id: "Spring",
      img: "/img/spring.png",
      active: false,
    },
    {
      id: "php",
      img: "/img/php.png",
      active: false,
    },
    {
      id: "Swift",
      img: "/img/swift.png",
      active: false,
    },
    {
      id: "Kotlin",
      img: "/img/kotlin.png",
      active: false,
    },
    {
      id: "TypeScript",
      img: "/img/typescript.png",
      active: false,
    },
  ]);
  let stack = props.stack;
  // console.log(stack);
  // console.log("-----");

  React.useEffect(() => {
    // let stack = props.stack;
    // setStacks(stack);
    Stack.map(item => {
      if (item.id === props.stack) {
        setStacks(item.img);
        return item;
      }
      return item;
    });
  }, [whatPage, reduxstack]);

  return (
    <div>
      {stacks && (
        <Grid
          display="flex"
          width="80%"
          height="85%"
          borderRadius="100%"
          bg="#fff"
          margin="-33px 0px"
          boxShadow="0 0 2px #ccc"
        >
          <IMGS src={stacks}></IMGS>
        </Grid>
      )}
    </div>
  );
};

const IMGS = styled.img`
  width: 80%;
  margin: auto;
  padding: 5px;
  align-items: center;
`;

export default PostStacks;
